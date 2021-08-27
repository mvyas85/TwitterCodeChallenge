package com.twitter.challenge.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.twitter.challenge.service.api.WeatherApi
import com.twitter.challenge.service.model.WeatherConditions
import com.twitter.challenge.util.TemperatureConverter
import com.twitter.challenge.util.TemperatureStatistics
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * WeatherViewModel class's responsibility is to fetch data from weather API and assign it to
 * Live data variable, which then can be observed by different views
 */
class WeatherViewModel : ViewModel() {

    private val TAG = this.javaClass.simpleName

    private val weatherApi by lazy { WeatherApi.create() }

    val cityName: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val currentTempCelsius: MutableLiveData<Float> by lazy { MutableLiveData<Float>() }
    val currentTempFahrenheit: MutableLiveData<Float> by lazy { MutableLiveData<Float>() }
    val currentWindSpeed: MutableLiveData<Double> by lazy { MutableLiveData<Double>() }
    val isCloudy: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val tempDeviation: MutableLiveData<Float> by lazy { MutableLiveData<Float>() }
    val loadingComplete: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    private var currentWeatherDisposable: Disposable? = null
    private var futureWeatherDisposable: Disposable? = null

    fun loadCurrentWeatherConditions() {
        currentWeatherDisposable = weatherApi.getCurrentWeather().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(5)
                .subscribe({ conditions ->
                    Log.d(TAG, "Object received  $conditions")
                    this.cityName.value = conditions.name
                    this.currentTempCelsius.value = conditions.weather.temp.toFloat()
                    this.currentTempFahrenheit.value =
                            TemperatureConverter.celsiusToFahrenheit(conditions.weather.temp.toFloat())
                    this.isCloudy.value = conditions.clouds.cloudiness > 50
                    this.currentWindSpeed.value = conditions.wind.speed
                    this.loadingComplete.value = true
                }, { Log.d(TAG, "loadCurrentWeatherConditions: $it") })
    }

    fun loadFutureWeatherConditions() {
        val futureTempList = mutableListOf<Double>()

        val observables = mutableListOf<Observable<WeatherConditions>>()
        for (i in 1..5) {
            observables.add(weatherApi.getFutureWeather(i)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).retry(5))
        }

        futureWeatherDisposable = Observable.merge(observables).subscribe({ conditions ->
            futureTempList.add(conditions.weather.temp
                    .also { println("Future Temperature: $it List: $futureTempList") })
                    .also {
                        tempDeviation.setValue(
                                TemperatureStatistics.standardDeviation(futureTempList).toFloat()
                                        .also { println("5 day Deviation: $it") })
                    }
        }, { Log.d(TAG, "loadFutureWeatherConditions: $it") })
    }
}

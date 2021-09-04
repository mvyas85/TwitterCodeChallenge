package com.twitter.challenge.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twitter.challenge.model.WeatherResponse
import com.twitter.challenge.network.repository.WeatherRepository
import com.twitter.challenge.util.TemperatureConverter
import com.twitter.challenge.util.TemperatureStatistics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * WeatherViewModel class's responsibility is to fetch data from weather API and assign it to
 * Live data variable, which then can be observed by different views
 */
@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    private val TAG = this.javaClass.simpleName

    val cityName: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val currentTempCelsius: MutableLiveData<Float> by lazy { MutableLiveData<Float>() }
    val currentTempFahrenheit: MutableLiveData<Float> by lazy { MutableLiveData<Float>() }
    val currentWindSpeed: MutableLiveData<Double> by lazy { MutableLiveData<Double>() }
    val isCloudy: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val tempDeviation: MutableLiveData<Double> by lazy { MutableLiveData<Double>() }
    val loadingComplete: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    val hasError: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun loadCurrentWeatherConditions() {

        Log.d(TAG, "loadCurrentWeatherConditions()")
        viewModelScope.launch {
            repository.getWeatherData().let { result ->
                when (result) {
                    is WeatherResponse.Success -> {
                        Log.d(TAG, "loadCurrentWeatherConditions() | Result success")
                        with(result.weatherConditions) {
                            cityName.value = name
                            currentTempCelsius.value = weather.temp.toFloat()
                            currentTempFahrenheit.value =
                                    TemperatureConverter.celsiusToFahrenheit(weather.temp.toFloat())
                            isCloudy.value = clouds.cloudiness > 50
                            currentWindSpeed.value = wind.speed
                            loadingComplete.value = true
                        }
                    }
                    is WeatherResponse.Error -> {
                        Log.d(TAG, "loadCurrentWeatherConditions() | Result error")
                        hasError.value = true
                        // Take care of Error handling gracefully Future TODO
                        // May be add fragment with empty data message
                    }
                }
            }
        }
    }

    fun loadFutureWeatherConditions() {

        Log.d(TAG, "loadFutureWeatherConditions()")
        viewModelScope.launch {
            var futureWeather = ArrayList<Double>()
            for (i in 1..5) {
                repository.getWeatherFutureData(i).let { result ->
                    when (result) {
                        is WeatherResponse.Success -> {
                            Log.d(TAG, "loadFutureWeatherConditions() | Result success")
                            futureWeather.add(result.weatherConditions.weather.temp)
                        }
                        is WeatherResponse.Error -> {
                            Log.d(TAG, "loadFutureWeatherConditions() | Result error")
                            hasError.value = true
                            // Take care of Error handling gracefully Future TODO
                            // May be add fragment with empty data message
                        }
                    }
                }.also {
                    if (futureWeather.size == 5) {
                        tempDeviation.value = String.format("%.2f", TemperatureStatistics.standardDeviation(futureWeather)).toDouble()
                                .also { println("5 day Deviation: $it") }

                    }
                }
            }
        }
    }

}

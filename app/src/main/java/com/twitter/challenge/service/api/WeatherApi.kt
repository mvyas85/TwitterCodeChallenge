package com.twitter.challenge.service.api

import com.twitter.challenge.service.model.WeatherConditions
import com.twitter.challenge.util.Constants
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {

    @GET("current.json")
    fun getCurrentWeather(): Observable<WeatherConditions>

    @GET("future_{day}.json")
    fun getFutureWeather(@Path("day") day: Int): Observable<WeatherConditions>

    companion object {
        fun create(): WeatherApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()

            return retrofit.create(WeatherApi::class.java)
        }
    }

}
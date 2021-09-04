package com.twitter.challenge.network.api

import com.twitter.challenge.model.WeatherConditions
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {
    @GET("current.json")
    fun getCurrentWeather(): Call<WeatherConditions>

    @GET("future_{day}.json")
    fun getFutureWeather(@Path("day") day: Int): Call<WeatherConditions>
}
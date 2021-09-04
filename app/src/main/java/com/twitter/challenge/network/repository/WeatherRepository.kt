package com.twitter.challenge.network.repository

import com.twitter.challenge.model.NetworkError
import com.twitter.challenge.model.WeatherResponse
import com.twitter.challenge.network.api.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface WeatherRepository {
    suspend fun getWeatherData(): WeatherResponse
    suspend fun getWeatherFutureData(day: Int): WeatherResponse
}

class WeatherRepositoryImpl @Inject constructor(var weatherService: WeatherApi) : WeatherRepository {

    override suspend fun getWeatherData(): WeatherResponse {
        return withContext(Dispatchers.IO) {
            var response = weatherService.getCurrentWeather().execute()
            if (response.isSuccessful) {
                WeatherResponse.Success(response.body()!!)
            } else {
                WeatherResponse.Error(NetworkError(response.errorBody().toString()))
            }
        }
    }

    override suspend fun getWeatherFutureData(day: Int): WeatherResponse {
        return withContext(Dispatchers.IO) {
            var response = weatherService.getFutureWeather(day).execute()
            if (response.isSuccessful) {
                WeatherResponse.Success(response.body()!!)
            } else {
                WeatherResponse.Error(NetworkError(response.errorBody().toString()))
            }
        }
    }
}
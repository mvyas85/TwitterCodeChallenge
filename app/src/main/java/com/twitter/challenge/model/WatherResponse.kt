package com.twitter.challenge.model

// A generic class that contains data and status about loading this data.
sealed class WeatherResponse {
    data class Success(val weatherConditions: WeatherConditions) : WeatherResponse()
    data class Error(val error: NetworkError) : WeatherResponse()
}

data class NetworkError(val error: String) : Exception()
package com.twitter.challenge.service.model;

data class WeatherConditions(
        val name: String,
        val weather: Weather,
        val wind: Wind,
        val clouds: Clouds
)
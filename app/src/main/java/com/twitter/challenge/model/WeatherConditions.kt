package com.twitter.challenge.model;

data class WeatherConditions(
        val name: String,
        val weather: Weather,
        val wind: Wind,
        val clouds: Clouds
)
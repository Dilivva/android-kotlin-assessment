package com.lexmerciful.deliveryweatherinsightsapp.domain.model

data class WeatherResponse(
    val id: Int?,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)

data class Main(val temp: Double)
data class Weather(val main: String, val description: String)
data class Wind(val speed: Double)
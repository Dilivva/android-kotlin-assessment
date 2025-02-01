package com.basebox.weatherinsights.data.model

data class InsightResponse(
    val temperature: Temperature,
    val weather: List<WeatherCondition>,
    val wind: Wind
)

data class Temperature(val temp: Double)
data class WeatherCondition(val description: String)
data class Wind(val speed: Double)

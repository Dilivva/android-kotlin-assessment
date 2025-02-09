package com.basebox.weatherinsights.data.model

data class InsightResponse(
    val main: Main,
    val weather: List<WeatherCondition>,
    val wind: Wind
)

data class Main(val temp: Double)
data class WeatherCondition(val description: String)
data class Wind(val speed: Double)

package com.lexmerciful.deliveryweatherinsightsapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("weather_history")
data class WeatherHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pickup: String,
    val dropoff: String,
    val recommendation: String,
    val timestamp: Long
)
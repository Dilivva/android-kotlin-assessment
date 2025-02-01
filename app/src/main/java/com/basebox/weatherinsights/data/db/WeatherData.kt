package com.basebox.weatherinsights.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_history")
data class WeatherData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val location: String,
    val temperature: Double,
    val description: String
)

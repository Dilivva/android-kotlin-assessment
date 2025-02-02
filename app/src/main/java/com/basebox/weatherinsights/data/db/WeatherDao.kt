package com.basebox.weatherinsights.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: WeatherData)

    @Query("SELECT * FROM weather_data ORDER BY id DESC LIMIT 5")
    fun getLast5Searches(): Flow<List<WeatherData>>
}

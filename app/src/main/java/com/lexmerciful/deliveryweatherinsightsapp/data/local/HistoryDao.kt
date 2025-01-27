package com.lexmerciful.deliveryweatherinsightsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lexmerciful.deliveryweatherinsightsapp.domain.model.WeatherHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert()
    suspend fun insertHistory(history: WeatherHistory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllHistory(histories: List<WeatherHistory>)

    @Query("SELECT * FROM weather_history ORDER BY timestamp DESC LIMIT 5")
    fun getAllHistory(): Flow<List<WeatherHistory>>

    @Query("DELETE FROM weather_history")
    fun deleteAllHistory()
}
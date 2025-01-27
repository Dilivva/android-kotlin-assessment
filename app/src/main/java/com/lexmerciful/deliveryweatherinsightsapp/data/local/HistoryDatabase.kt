package com.lexmerciful.deliveryweatherinsightsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lexmerciful.deliveryweatherinsightsapp.domain.model.WeatherHistory

@Database(entities = [WeatherHistory::class], version = 1)
abstract class HistoryDatabase : RoomDatabase(){

    abstract fun historyDao(): HistoryDao

}
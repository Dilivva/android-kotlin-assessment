package com.basebox.weatherinsights.di

import android.app.Application
import androidx.room.Room
import com.basebox.weatherinsights.data.db.RoomDB
import com.basebox.weatherinsights.data.db.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InsightsModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): RoomDB {
        return Room.databaseBuilder(app, RoomDB::class.java, "weather_database").build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(db: RoomDB): WeatherDao {
        return db.weatherDao()
    }
}
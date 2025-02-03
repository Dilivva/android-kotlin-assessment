package com.basebox.weatherinsights.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.basebox.weatherinsights.data.db.RoomDB
import com.basebox.weatherinsights.data.db.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InsightsModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RoomDB {
        return RoomDB.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideWeatherDao(db: RoomDB): WeatherDao {
        return db.weatherDao()
    }
}
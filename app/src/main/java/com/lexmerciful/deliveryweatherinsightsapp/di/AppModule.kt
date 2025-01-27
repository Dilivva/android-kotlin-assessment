package com.lexmerciful.deliveryweatherinsightsapp.di

import android.content.Context
import androidx.room.Room
import com.lexmerciful.deliveryweatherinsightsapp.data.local.HistoryDao
import com.lexmerciful.deliveryweatherinsightsapp.data.local.HistoryDatabase
import com.lexmerciful.deliveryweatherinsightsapp.data.remote.ApiService
import com.lexmerciful.deliveryweatherinsightsapp.data.repository.WeatherRepository
import com.lexmerciful.deliveryweatherinsightsapp.util.Constants.BASE_URL
import com.lexmerciful.deliveryweatherinsightsapp.util.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideHistoryDatabase(
        @ApplicationContext context: Context
    ): HistoryDatabase {
        return Room.databaseBuilder(context, HistoryDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(
        historyDatabase: HistoryDatabase
    ): HistoryDao = historyDatabase.historyDao()

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(
        apiService: ApiService,
        historyDao: HistoryDao
    ): WeatherRepository {
        return WeatherRepository(apiService, historyDao)
    }

}
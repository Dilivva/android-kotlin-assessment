package com.solt.deliveryweatherinsighttest.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.solt.deliveryweatherinsighttest.data.remote.Utils
import com.solt.deliveryweatherinsighttest.data.remote.dao.WeatherReportDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

//Main Module for Application
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    //First Retrofit ,it will be singleton
    @Provides
    @Singleton
    fun providesRetrofit(@ApplicationContext context: Context):Retrofit{
       //Converter Factory for Json
        val jsonConverter =  Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType())
       val retrofit = Retrofit.Builder().baseUrl(Utils.BASE_WEATHER_URL).addConverterFactory(jsonConverter).build()
        return retrofit
    }
    //Next the weather api service , will also be singleton
    @Provides
    @Singleton
    fun providesWeatherApiService (retrofit:Retrofit):WeatherReportDAO{
        return retrofit.create(WeatherReportDAO::class.java)
    }
}
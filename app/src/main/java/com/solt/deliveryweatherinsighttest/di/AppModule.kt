package com.solt.deliveryweatherinsighttest.di

import android.content.Context
import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.solt.deliveryweatherinsighttest.data.remote.Utils
import com.solt.deliveryweatherinsighttest.data.remote.dao.WeatherReportDAO
import com.solt.deliveryweatherinsighttest.data.remote.repository.WeatherRepository
import com.solt.deliveryweatherinsighttest.data.remote.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import javax.inject.Singleton

//Main Module for Application
@Module
@InstallIn(SingletonComponent::class)
 class AppModule {
    //First Retrofit ,it will be singleton
    @Provides
    @Singleton
    fun providesRetrofit(@ApplicationContext context: Context):Retrofit{
       //Converter Factory for Json
        val jsonConverter =  Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType())
        val loggingInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request().toString()
                Log.i("Httr",request)
                val response = chain.proceed(chain.request())
                Log.i("Httr",response.toString())
                return response
            }


        }
        val customOkHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
       val retrofit = Retrofit.Builder().baseUrl(Utils.BASE_WEATHER_URL).addConverterFactory(jsonConverter).client(customOkHttpClient).build()
        return retrofit
    }
    //Next the weather api service , will also be singleton
    @Provides
    @Singleton
    fun providesWeatherApiService (retrofit:Retrofit):WeatherReportDAO{
        return retrofit.create(WeatherReportDAO::class.java)
    }

     @Provides
     fun providesWeatherRepository( apiService:WeatherReportDAO): WeatherRepository{
         return WeatherRepositoryImpl(apiService)
     }
}
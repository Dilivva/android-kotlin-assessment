package com.solt.deliveryweatherinsighttest.data.remote

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.solt.deliveryweatherinsighttest.data.remote.dao.WeatherReportDAO
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitInstance {
    const val BASE_URL = "https://api.openweathermap.org/data/2.5"
    val JsonConverter = Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType())
    lateinit var retrofit: Retrofit
    lateinit var weatherApiService :WeatherReportDAO

    fun initialize(context: Context){
        retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(JsonConverter).build()
        weatherApiService = retrofit.create(WeatherReportDAO::class.java)
    }
}
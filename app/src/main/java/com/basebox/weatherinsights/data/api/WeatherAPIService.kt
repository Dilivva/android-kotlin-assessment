package com.basebox.weatherinsights.data.api

import com.basebox.weatherinsights.data.model.InsightResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") location: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): InsightResponse
}
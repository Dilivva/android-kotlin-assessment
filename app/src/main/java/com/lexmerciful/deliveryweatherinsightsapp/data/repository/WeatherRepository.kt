package com.lexmerciful.deliveryweatherinsightsapp.data.repository

import android.util.Log
import com.lexmerciful.deliveryweatherinsightsapp.data.local.HistoryDao
import com.lexmerciful.deliveryweatherinsightsapp.data.remote.ApiService
import com.lexmerciful.deliveryweatherinsightsapp.domain.model.WeatherResponse
import com.lexmerciful.deliveryweatherinsightsapp.util.Constants.API_KEY
import com.lexmerciful.deliveryweatherinsightsapp.util.Resource
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: ApiService,
    private val historyDao: HistoryDao
) {

    suspend fun fetchWeather(location: String): Resource<WeatherResponse> {
        return try {
            val response = apiService.getWeather(location, API_KEY)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Resource.success(body)
                } else {
                    Resource.error("Server returned an empty response.")
                }
            } else {
                Resource.error("${response.code()}: Unable to reach server. Please check your connection")
            }
        } catch (e: Exception) {
            Resource.error("Unable to reach server. Please check your connection")
        }
    }
}
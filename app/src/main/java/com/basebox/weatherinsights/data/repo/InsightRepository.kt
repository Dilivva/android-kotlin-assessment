package com.basebox.weatherinsights.data.repo

import android.util.Log
import com.basebox.weatherinsights.data.api.APIClient
import com.basebox.weatherinsights.data.db.WeatherDao
import com.basebox.weatherinsights.data.db.WeatherData
import com.basebox.weatherinsights.data.model.InsightResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.basebox.weatherinsights.BuildConfig

class InsightRepository @Inject constructor(private val dao: WeatherDao) {
    val apiKey = BuildConfig.API_KEY
    suspend fun fetchData(location: String): InsightResponse {
        return try {
            val res = APIClient.api.getWeather(location, apiKey)
            Log.d("API Response", "Weather Data: $res")
            return res
        } catch (err: Exception){
            Log.e("API Error", "Failed to fetch weather data", err)
            throw err
        }

    }

    val allWeatherData: Flow<List<WeatherData>> = dao.getLast5Searches()
        .onEach { Log.d("InsightRepository", "Fetched from Room: $it") }
        .flowOn(Dispatchers.IO)

    suspend fun insertWeatherData(weather: WeatherData) {
        dao.insert(weather)
    }
}
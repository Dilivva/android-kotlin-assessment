package com.basebox.weatherinsights.data.repo

import android.util.Log
import com.basebox.weatherinsights.data.api.APIClient
import com.basebox.weatherinsights.data.model.InsightResponse

class InsightRepository {
    suspend fun fetchData(location: String): InsightResponse {
        return try {
            val res = APIClient.api.getWeather(location, "722f8452241fd8024bfeeaaa3eb998bc")
            Log.d("API Response", "Weather Data: $res")
            return res
        } catch (e: Exception){
            Log.e("API Error", "Failed to fetch weather data", e)
            // Handle error, possibly return a default value or show an error message
            throw e
        }

    }
}
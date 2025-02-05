package com.solt.deliveryweatherinsighttest.data.remote.dao

import com.solt.deliveryweatherinsighttest.data.remote.Utils
import com.solt.deliveryweatherinsighttest.data.remote.model.weather.WeatherReportModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherReportDAO {
    //The Weather Api Interface for Retrofit
    @GET()
  suspend  fun getWeatherReportByLongitudeAndLatitude( @Url url:String,@Query("lon")longitude:Double,@Query("lat")latitude:Double,@Query("appId")apiKey:String):WeatherReportModel
}
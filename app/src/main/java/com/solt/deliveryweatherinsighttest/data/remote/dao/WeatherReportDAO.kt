package com.solt.deliveryweatherinsighttest.data.remote.dao

import com.solt.deliveryweatherinsighttest.data.remote.model.WeatherReportModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherReportDAO {
    //The Weather Api Interface for Retrofit
    @GET("/weather")
  suspend  fun getWeatherReportByLongitudeAndLatitude(@Query("lon")longitude:Double,@Query("lat")latitude:Double,@Query("appid")apiKey:String):WeatherReportModel
}
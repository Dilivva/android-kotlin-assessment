package com.solt.deliveryweatherinsighttest.data.remote.repository


import com.solt.deliveryweatherinsighttest.data.remote.model.util.OperationResult
//Abstraction for testing or when change is needed
interface WeatherRepository{
     suspend fun getWeatherReportByLocation(longitude:Double,latitude:Double):OperationResult
}
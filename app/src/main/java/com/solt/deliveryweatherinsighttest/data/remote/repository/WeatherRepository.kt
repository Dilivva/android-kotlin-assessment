package com.solt.deliveryweatherinsighttest.data.remote.repository

import android.util.Log
import com.solt.deliveryweatherinsighttest.BuildConfig
import com.solt.deliveryweatherinsighttest.data.remote.dao.WeatherReportDAO
import com.solt.deliveryweatherinsighttest.data.remote.model.util.OperationResult
import com.solt.deliveryweatherinsighttest.data.remote.model.weather.WeatherReportModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
//Abstraction for testing or when change is needed
interface WeatherRepository{
    fun getWeatherReportByLocation(longitude:Double,latitude:Double):OperationResult
}
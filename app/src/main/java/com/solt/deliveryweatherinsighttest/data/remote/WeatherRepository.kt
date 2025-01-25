package com.solt.deliveryweatherinsighttest.data.remote

import com.solt.deliveryweatherinsighttest.BuildConfig
import com.solt.deliveryweatherinsighttest.data.remote.dao.WeatherReportDAO
import com.solt.deliveryweatherinsighttest.data.remote.model.WeatherReportModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(val api :WeatherReportDAO) {
 suspend  fun getWeatherReportByLongitudeAndLatitude(longitude:Double,latitude:Double):WeatherReportModel?{
        return withContext(Dispatchers.IO){
            try{
            api.getWeatherReportByLongitudeAndLatitude(longitude,latitude,BuildConfig.api_key)
            }catch (e:Exception){
                if(e is CancellationException) throw e
                else null
            }
        }
    }
    //A proper error handling class should be made available stating either success or failure and what caused the failure
}
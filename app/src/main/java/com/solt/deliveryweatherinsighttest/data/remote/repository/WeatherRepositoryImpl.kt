package com.solt.deliveryweatherinsighttest.data.remote.repository

import com.solt.deliveryweatherinsighttest.BuildConfig
import com.solt.deliveryweatherinsighttest.data.remote.Utils
import com.solt.deliveryweatherinsighttest.data.remote.dao.WeatherReportDAO
import com.solt.deliveryweatherinsighttest.data.remote.model.util.OperationResult
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl@Inject constructor(val apiService:WeatherReportDAO):WeatherRepository {
    override  suspend fun getWeatherReportByLocation(longitude: Double, latitude: Double):OperationResult {
   //Runs on a suspend function on the Io Dispatcher
     return withContext(Dispatchers.IO){
        try {
            val result = apiService.getWeatherReportByLongitudeAndLatitude(Utils.BASE_WEATHER_URL,longitude,latitude,BuildConfig.api_key)
            OperationResult.Success(result)
        }catch (e:CancellationException){
            //Throw it back as the coroutine needs it
            throw  e
        }catch (e:Exception){
            OperationResult.Failure(e)
        }
    }
    }
}
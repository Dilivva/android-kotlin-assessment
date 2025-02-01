package com.solt.deliveryweatherinsighttest.ui.viewmodel

import androidx.lifecycle.ViewModel

import com.solt.deliveryweatherinsighttest.data.remote.model.util.OperationResult
import com.solt.deliveryweatherinsighttest.data.remote.repository.WeatherRepository
import com.solt.deliveryweatherinsighttest.data.remote.model.weather.WeatherReportModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.IllegalStateException

@HiltViewModel
class MainPageViewModel(val weatherRepo: WeatherRepository): ViewModel() {
//Get the weather report when never there is a click on the map
    suspend fun getWeatherReport(longitude:Double, latitude:Double,onSuccess:(WeatherReportModel)->Unit,onFailure:(Exception)->Unit){
    when(val result = weatherRepo.getWeatherReportByLocation(longitude,latitude)){
           is OperationResult.Failure -> onFailure(result.e)
           is OperationResult.Success<*> -> {
               val data  = result.data as? WeatherReportModel
               //If it is not a weather report return onFailure
               if (data == null) onFailure(IllegalStateException("Data is not of type required"))
               else onSuccess(data)

           }
       }
    }

}
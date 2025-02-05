package com.solt.deliveryweatherinsighttest.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solt.deliveryweatherinsighttest.data.database.model.LocationHistoryEntity
import com.solt.deliveryweatherinsighttest.data.database.repository.LocationHistoryRepository
import com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward.NameToLocation
import com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward.NameToLocationDisplayModel

import com.solt.deliveryweatherinsighttest.data.remote.model.util.OperationResult
import com.solt.deliveryweatherinsighttest.data.remote.repository.WeatherRepository
import com.solt.deliveryweatherinsighttest.data.remote.model.weather.WeatherReportModel
import com.solt.deliveryweatherinsighttest.data.remote.repository.GeocodingRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(val weatherRepo: WeatherRepository, val locationHistoryRepository: LocationHistoryRepository,val geocodingRepositoryImpl: GeocodingRepositoryImpl): ViewModel() {
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
     //We need to add the location to location history when the user long clicks
    fun insertLocationIntoHistory(latitude: Double,longitude: Double){
        viewModelScope.launch {
            val presentTime = Date()
            val currentLocationHistoryEntity = LocationHistoryEntity(longitude,latitude,presentTime)
            locationHistoryRepository.insertLocationHistory(currentLocationHistoryEntity)
        }
    }

    //We will search for the  location if there is an error we just return null
   suspend fun searchForLocationByName(name:String):NameToLocationDisplayModel?{
        val result = geocodingRepositoryImpl.searchForLocationByName(name)
        return when(result){
           is OperationResult.Failure -> {
               Log.i("Search ","${result.e.message}")
               null
           }
           is OperationResult.Success<*> -> result.data as NameToLocationDisplayModel
       }
    }

}
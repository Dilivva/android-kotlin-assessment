package com.solt.deliveryweatherinsighttest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solt.deliveryweatherinsighttest.data.remote.WeatherRepository
import com.solt.deliveryweatherinsighttest.data.remote.model.WeatherReportModel

class MainViewModel(val weatherRepo:WeatherRepository): ViewModel() {
    suspend fun getWeather(longitude:Double,latitude:Double,onSuccess:(WeatherReportModel)->Unit){
        //This will get the weather report if there are no exceptions

        val weatherReport = weatherRepo.getWeatherReportByLongitudeAndLatitude(longitude,latitude)
        if (weatherReport!= null ){
            onSuccess(weatherReport)
        }
    }
class MainViewModelFactory(val weatherRepo: WeatherRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(weatherRepo) as T
        }
        else throw IllegalStateException()
    }
}
}
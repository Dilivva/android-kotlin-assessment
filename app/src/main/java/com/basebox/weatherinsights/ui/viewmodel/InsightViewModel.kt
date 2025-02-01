package com.basebox.weatherinsights.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.basebox.weatherinsights.data.db.WeatherData
import com.basebox.weatherinsights.data.model.InsightResponse
import com.basebox.weatherinsights.data.repo.InsightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsightViewModel @Inject constructor(private val repository: InsightRepository) : ViewModel() {
    private val _weatherData = MutableLiveData<InsightResponse?>()
    val weatherData: LiveData<InsightResponse?> = _weatherData

    private val _weatherDropOffData = MutableLiveData<InsightResponse?>()
    val weatherDropOffData: LiveData<InsightResponse?> = _weatherDropOffData

    fun fetchData(location: String, dropoff: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = repository.fetchData(location)
                _weatherData.postValue(response)
//
            } catch (e: Exception) {
                Log.e("InsightViewModel", "Error fetching weather: ${e.message}")
                _weatherData.value = null
            }
        }
    }

    val savedData = repository.allWeatherData.asLiveData()

    fun saveData(location: String, temp: Double, description: String) {
        viewModelScope.launch {
            repository.insertWeatherData(WeatherData(0, location, temp, description ))
            Log.d("InsightViewModel", "Saved: ${WeatherData(0, location, temp, description )}")
        }
    }
}

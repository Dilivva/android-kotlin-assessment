package com.lexmerciful.deliveryweatherinsightsapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexmerciful.deliveryweatherinsightsapp.data.repository.WeatherRepository
import com.lexmerciful.deliveryweatherinsightsapp.domain.model.WeatherResponse
import com.lexmerciful.deliveryweatherinsightsapp.util.Resource
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class WeatherViewmodel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val _pickUpWeather = MutableLiveData<WeatherResponse?>()
    val pickupWeather: MutableLiveData<WeatherResponse?> = _pickUpWeather

    private val _dropOffWeather = MutableLiveData<WeatherResponse?>()
    val dropOffWeather: MutableLiveData<WeatherResponse?> = _dropOffWeather

    private val _deliveryRecommendation = MutableLiveData<String?>()
    val deliveryRecommendation: MutableLiveData<String?> = _deliveryRecommendation

    fun getWeather(pickUpLocation: String, dropOffLocation: String) {
        viewModelScope.launch {
            try {
                val pickupWeatherInfo = weatherRepository.fetchWeather(pickUpLocation)
                _pickUpWeather.value = pickupWeatherInfo.data

                val dropOffWeatherInfo = weatherRepository.fetchWeather(dropOffLocation)
                _dropOffWeather.value = dropOffWeatherInfo.data

                if (pickupWeather.value != null && dropOffWeather.value != null) {
                    val recommendation = getRecommendation(pickupWeather.value!!, dropOffWeather.value!!)
                    _deliveryRecommendation.value = recommendation
                }

            } catch (e: Exception) {
                Timber.tag("WeatherViewmodel").e("Error: %s", e.message)
            }
        }
    }

    private fun getRecommendation(pickupWeather: WeatherResponse, dropoffWeather: WeatherResponse): String {
        val unsafeConditions = listOf("thunderstorm", "heavy rain")
        val isUnsafe = pickupWeather.weather.any { it.main.lowercase() in unsafeConditions } ||
                dropoffWeather.weather.any { it.main.lowercase() in unsafeConditions }

        return if (isUnsafe) "Delay advised" else "Safe for delivery"
    }

}
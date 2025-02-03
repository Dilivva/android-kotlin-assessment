package com.lexmerciful.deliveryweatherinsightsapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexmerciful.deliveryweatherinsightsapp.data.repository.WeatherRepository
import com.lexmerciful.deliveryweatherinsightsapp.domain.model.WeatherResponse
import com.lexmerciful.deliveryweatherinsightsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherViewmodel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val _pickUpWeather = MutableLiveData<WeatherResponse?>()
    val pickupWeather: MutableLiveData<WeatherResponse?> = _pickUpWeather

    private val _dropOffWeather = MutableLiveData<WeatherResponse?>()
    val dropOffWeather: MutableLiveData<WeatherResponse?> = _dropOffWeather

    private val _weatherData = MutableLiveData<Pair<WeatherResponse, WeatherResponse>>()
    val weatherData: LiveData<Pair<WeatherResponse, WeatherResponse>> = _weatherData

    private val _deliveryRecommendation = MutableLiveData<String?>()
    val deliveryRecommendation: MutableLiveData<String?> = _deliveryRecommendation

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> = _errorState

    fun getWeather(pickUpLocation: String, dropOffLocation: String) {
        viewModelScope.launch {
            _loadingState.value = true
            _errorState.value = null

            try {
                val pickupWeatherInfo = weatherRepository.fetchWeather(pickUpLocation)
                _pickUpWeather.value = pickupWeatherInfo.data

                val dropOffWeatherInfo = weatherRepository.fetchWeather(dropOffLocation)
                _dropOffWeather.value = dropOffWeatherInfo.data

                _weatherData.value = Pair(pickupWeatherInfo.data!!, dropOffWeatherInfo.data!!)

                if (pickupWeather.value != null && dropOffWeather.value != null) {
                    val recommendation = getRecommendation(pickupWeather.value!!, dropOffWeather.value!!)
                    _deliveryRecommendation.value = recommendation
                }

            } catch (e: Exception) {
                _errorState.value = e.message
                Timber.tag("WeatherViewmodel").e("Error: %s", e.message)
            } finally {
                _loadingState.value = false
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
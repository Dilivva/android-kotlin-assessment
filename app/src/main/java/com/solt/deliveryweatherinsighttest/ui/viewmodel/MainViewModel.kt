package com.solt.deliveryweatherinsighttest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solt.deliveryweatherinsighttest.data.remote.repository.WeatherRepository
import com.solt.deliveryweatherinsighttest.data.remote.model.weather.WeatherReportModel

class MainViewModel(val weatherRepo: WeatherRepository): ViewModel() {


}
package com.solt.deliveryweatherinsighttest

import android.app.Application
import com.solt.deliveryweatherinsighttest.data.remote.RetrofitInstance

class WeatherApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        //Initialize Retrofit Instance Here
        //Dependency Injection could have been used but for the sake of time
        RetrofitInstance.initialize(this)
    }
}
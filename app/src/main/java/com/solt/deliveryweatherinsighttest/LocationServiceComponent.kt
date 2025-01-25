package com.solt.deliveryweatherinsighttest

import android.app.Service
import android.content.Intent
import android.os.IBinder

class LocationServiceComponent:Service() {
    override fun onBind(intent: Intent?): IBinder? {
     return null
    }
}
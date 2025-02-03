package com.solt.deliveryweatherinsighttest.ui.maps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.solt.deliveryweatherinsighttest.databinding.HomeMarkerViewLayoutBinding
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.plugins.markerview.MarkerView
import java.lang.ref.WeakReference

class CustomMarkerView(var lat:Double,var lon:Double,val markerView:MarkerView) {
    //This will be our custom marker  view
    //It will have the icon , the name of the place (will be gotten with a geocoding api) and the weather condition

    fun  setLatLng(latitude:Double,longitude:Double){
        lon = longitude
        lat = latitude
        markerView.setLatLng(LatLng(latitude,longitude))

    }
    init {

    }
}
package com.solt.deliveryweatherinsighttest.ui.maps

import FixedMarkerView
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.solt.deliveryweatherinsighttest.databinding.HomeMarkerViewLayoutBinding
import com.solt.deliveryweatherinsighttest.ui.pages.MainPage
import kotlinx.coroutines.launch
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.plugins.markerview.MarkerView
import java.lang.ref.WeakReference

class CustomMarkerView(var lat:Double,var lon:Double,view: View,val page:MainPage,val map:MapLibreMap) {
    //This will be our custom marker  view
    //It will have the icon , the name of the place (will be gotten with a geocoding api) and the weather condition
    val markerView:FixedMarkerView
    fun  setLatLng(latitude:Double,longitude:Double){
        lon = longitude
        lat = latitude
        markerView.setLatLng(LatLng(latitude,longitude))
        updateCameraToCurrentMarkerLocation()
    }
    init {
        //We will first create the marker view from the view
        markerView = FixedMarkerView(LatLng(lat,lon),view,map)
     //When created on the marker view click listener will will update the bottom sheet
        markerView.view.setOnClickListener{
            Log.i("Weather Report","Weather report gotten ")
            updateCameraToCurrentMarkerLocation()
            getWeatherReportForCurrentMarkerLocation()
        }
    }
    fun getWeatherReportForCurrentMarkerLocation(){
        page.viewLifecycleOwner.lifecycleScope.launch {
            //Get the weather report and update the bottom sheet
            Log.i("Weather Report","Weather report gotten ")
            page.mainPageViewModel.getWeatherReport(lat,lon,{page.updateBottomSheet(it)}){}
        }
    }
    fun updateCameraToCurrentMarkerLocation(){
        map.animateCamera(CameraUpdateFactory.newLatLng(LatLng(this.lat,this.lon)))
    }
}
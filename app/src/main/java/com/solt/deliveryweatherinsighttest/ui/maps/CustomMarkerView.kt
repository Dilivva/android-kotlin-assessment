package com.solt.deliveryweatherinsighttest.ui.maps

import FixedMarkerView
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.solt.deliveryweatherinsighttest.R
import com.solt.deliveryweatherinsighttest.data.database.model.StationEntity
import com.solt.deliveryweatherinsighttest.data.database.model.StationType
import com.solt.deliveryweatherinsighttest.databinding.HomeMarkerViewLayoutBinding
import com.solt.deliveryweatherinsighttest.ui.pages.MainPage
import kotlinx.coroutines.launch
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import org.maplibre.android.plugins.markerview.MarkerView
import org.maplibre.android.plugins.markerview.MarkerViewManager
import java.lang.ref.WeakReference

open class CustomMarkerView(var lat:Double, var lon:Double,
                            markerType: MarkerType, val page:MainPage, val map:MapLibreMap) {
    //This will be our custom marker  view
    //It will have the icon , the name of the place (will be gotten with a geocoding api) and the weather condition
    val markerView:FixedMarkerView
    val binding : HomeMarkerViewLayoutBinding
    fun  setLatLng(latitude:Double,longitude:Double){
        lon = longitude
        lat = latitude
        markerView.setLatLng(LatLng(latitude,longitude))

    }
    init {
        // we fixed the marker not responding to click issues
        //The parent (map view ) was intercepting the click by the gestures
       binding = HomeMarkerViewLayoutBinding.inflate(page.layoutInflater,page.binding.mapView,false)
        //We get the icon based on the marker type
        val resourceId = getDrawableForMarkerType(markerType)
        //We then inflate it
        val drawable = ResourcesCompat.getDrawable(page.resources,resourceId,page.requireActivity().theme)
        binding.icon.setImageDrawable(drawable)
        binding.icon.setOnClickListener{
            updateCameraToCurrentMarkerLocation()
            getWeatherReportForCurrentMarkerLocation()
        }
        //We will first create the marker view from the view
        markerView = FixedMarkerView(LatLng(lat,lon),binding.root,map)
     //When created on the marker view click listener will will update the bottom sheet
        //If the type is of location update immediately since the we need to know it as the user long clicks it
        if (markerType == MarkerType.LOCATION)getWeatherReportForCurrentMarkerLocation()

    }

    companion object{
        fun getMarkerTypeForStationType(stationType: StationType):MarkerType{
            val resource = when(stationType){
                StationType.DELIVERY -> MarkerType.DELIVERY
                StationType.PICKUP -> MarkerType.PICKUP
            }
            return resource
        }
    }
    //Based on the marker type i want there to be a different logo
    fun getDrawableForMarkerType(markerType: MarkerType):Int{
        val resource = when(markerType){
            MarkerType.HOME -> R.drawable.home_icon
            MarkerType.LOCATION -> R.drawable.location_icon
            MarkerType.DELIVERY -> R.drawable.delivery_icon
            MarkerType.PICKUP -> R.drawable.pickup_icon
        }
        return resource
    }


   open fun getWeatherReportForCurrentMarkerLocation(){
        page.viewLifecycleOwner.lifecycleScope.launch {
            //Get the weather report and update the bottom sheet
            Log.i("Weather Report","Weather report gotten ")
            page.mainPageViewModel.getWeatherReport(lat,lon,{
                //Now we will try and get the name from the weather report
                    //If not available we will use the one give to us
                   page. viewLifecycleOwner.lifecycleScope.launch {
                        val result = page.mainPageViewModel.getLocationNameByLatLng(lat,lon)
                        val name = result?.listOfSearchResults?.get(0)?.name
                        val nameOfLocation = name?:it.name?:"Unknown"
                        page.binding.nameOfLocation.text = nameOfLocation
                    }
                page.updateBottomSheet(it)
                setClickListenersForButtons()

            }){}
        }
    }


  open   fun setClickListenersForButtons(){
         page.binding.apply {
             deliveryButton.apply {
                 visibility = View.VISIBLE
                 text = "Delivery Station"
             }
             pickupButton.apply {
                 visibility = View.VISIBLE
                 text = "Pickup Station"
             }

         }
        //The delivery and pickup button will add markers to the current location
        page.binding.deliveryButton.setOnClickListener {
            val stationEntity = StationEntity(lon,lat,StationType.DELIVERY)
            page.mainPageViewModel.insertStationByLatLng(stationEntity)
        }
        page.binding.pickupButton.setOnClickListener {
            val stationEntity = StationEntity(lon,lat,StationType.PICKUP)
            page.mainPageViewModel.insertStationByLatLng(stationEntity)
        }
    }
    fun updateCameraToCurrentMarkerLocation(){
        map.animateCamera(CameraUpdateFactory.newLatLng(LatLng(this.lat,this.lon)))
    }
}
enum class MarkerType{
    HOME, LOCATION,DELIVERY,PICKUP
}
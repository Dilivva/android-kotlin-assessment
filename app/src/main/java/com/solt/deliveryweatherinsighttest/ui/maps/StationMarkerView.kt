package com.solt.deliveryweatherinsighttest.ui.maps

import android.view.View
import com.solt.deliveryweatherinsighttest.data.database.model.StationEntity
import com.solt.deliveryweatherinsighttest.data.database.model.StationType
import com.solt.deliveryweatherinsighttest.ui.pages.MainPage
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.plugins.markerview.MarkerViewManager

class StationMarkerView( lat:Double, lon:Double,markerType: MarkerType, page: MainPage,  map: MapLibreMap):CustomMarkerView(lat,lon,markerType,page,map) {
    val stationType:StationType?
    //This is a subclass of the marker view for stations
    init {
      stationType = when(markerType){
          MarkerType.PICKUP->StationType.PICKUP
          MarkerType.DELIVERY->StationType.DELIVERY
          else->null
      }
    }

    //It will contain delete marker to delete the marker from the marker view manager and from the database

    fun deleteStationWithMarker() {
        if (stationType != null) {
            val stationEntity = StationEntity(this.lon, this.lat,stationType)
            page.mainPageViewModel.deleteStationByLatLng(stationEntity)
            //We will want to clear the bottom sheet
            page.clearBottomSheet()
        }
    }

    //We will also want to override the display weatherInformation to add that one of the buttons will delete the marker

    override fun setClickListenersForButtons() {
        page.binding.pickupButton.apply {
            text = "Delete Station"
            setOnClickListener {
                deleteStationWithMarker()
            }
        }
        page.binding.deliveryButton.visibility = View.GONE
    }
}
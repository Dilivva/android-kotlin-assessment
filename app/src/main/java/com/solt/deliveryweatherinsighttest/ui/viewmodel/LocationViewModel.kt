package com.solt.deliveryweatherinsighttest.ui.viewmodel

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.solt.deliveryweatherinsighttest.utils.LocationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(val locationService: LocationService):ViewModel(){
     val didUserChangeLocationSettings = Channel<Boolean>()
    fun requestLocationUpdates(fragment: Fragment,locationCallback: LocationCallback,onFailure: (String) -> Unit){
        //the location updates should not change unless the user moves
        val locationUpdateRequest = LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY,5000).setMinUpdateDistanceMeters(1.0f).build()
        //Check if the settings are all set
        val locationSettingRequest = LocationSettingsRequest.Builder().addLocationRequest(locationUpdateRequest).build()
        locationService.checkIfLocationSettingsAreSet(fragment.requireActivity(),locationSettingRequest,{
            //If the settings are all set
            locationService.getContinuousLocationUpdate(fragment.requireActivity() as AppCompatActivity,locationUpdateRequest,locationCallback)
        }){
           val activityResult =  didUserChangeLocationSettings.tryReceive().getOrNull()
            if (activityResult == true) {
                //If the user accepted all the settings we try to get requests again
                //it should work this time
                requestLocationUpdates(fragment,locationCallback,onFailure)
            }else if (activityResult == false){
                //We will display on Failure
                onFailure("The location settings are required for continuous updates")
            }
        }

    }
     fun checkIfLocationPermissionsHaveBeenGranted(activity: AppCompatActivity) = locationService.checkIfUserHasAcceptedLocationPermission(activity)
    fun getLocation(activity: AppCompatActivity,onSuccess:(LocationService.LocationModel)->Unit,onFailure: (String) -> Unit){
        val location = locationService.getLocation(activity)
        //When the location is ready we set the camera (This is the map library specific) to the users location
        if (location == null){
           onFailure("Location Permissions have not been accepted")

        }else{
            location.addOnCompleteListener {
                if(it.isSuccessful){
                   onSuccess(LocationService.LocationModel(it.result.longitude,it.result.latitude))
                }
                else{
                    onFailure(it.exception?.message?:"Error get location")
                }
            }
        }
    }
    fun removeLocationUpdates( fragment: Fragment,callback: LocationCallback) {
        locationService.removeLocationUpdates(fragment.requireActivity() as AppCompatActivity,callback)
    }


}
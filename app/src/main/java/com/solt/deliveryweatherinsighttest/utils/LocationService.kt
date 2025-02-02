package com.solt.deliveryweatherinsighttest.utils

import android.Manifest
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.solt.deliveryweatherinsighttest.data.remote.model.util.OperationResult
import javax.inject.Inject
import javax.inject.Singleton
const val REQUEST_LOCATION_SETTINGS_CHECK = 23
@Singleton
class LocationService @Inject constructor() {
    //This class gets the users current location (longitude and latitude ) to be displayed in the map class
    fun checkIfUserHasAcceptedLocationPermission(activity:AppCompatActivity):Boolean{
        //Check whether user has only accepted the fine and coarse location permissions
        val isCoarseLocationGranted = activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        val isFineLocationGranted = activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        return if (isFineLocationGranted == PackageManager.PERMISSION_GRANTED && isCoarseLocationGranted == PackageManager.PERMISSION_GRANTED) true
        else false
    }
  fun getLocation(activity: AppCompatActivity): Task<Location>? {
     val fusedLocationService = LocationServices.getFusedLocationProviderClient(activity)
      if(checkIfUserHasAcceptedLocationPermission(activity)){

        return fusedLocationService.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,null)

      }
      return null

 }
    //When we are requesting continuous location updates we need to check if the appropriate settings are turned on for location updates
    //Based on our location update request
    fun checkIfLocationSettingsAreSet(activity: FragmentActivity,locationSettingsRequest: LocationSettingsRequest, onSuccess:()->Unit,onActivityResult:()->Unit){
        val settingsClient = LocationServices.getSettingsClient(activity)
        val task =  settingsClient.checkLocationSettings(locationSettingsRequest)
        task.addOnSuccessListener {
          onSuccess()
        }
        task.addOnFailureListener {
            if (it is ResolvableApiException){
                try {
                    it.startResolutionForResult(activity, REQUEST_LOCATION_SETTINGS_CHECK)
                    //The reason for this is that when the onActivityResult is called the on Failure will be called with the value
                    onActivityResult()
                }catch (e :SendIntentException){//Ignore the error}
//On the activity we will check if the intent where still solved
            }
        }
    }

}
    fun getContinuousLocationUpdate(activity: AppCompatActivity,locationRequest: LocationRequest,locationCallBack:LocationCallback){
        val fusedLocationService = LocationServices.getFusedLocationProviderClient(activity)
        //Lets create the location update request
        if (checkIfUserHasAcceptedLocationPermission(activity)){
            fusedLocationService.requestLocationUpdates(locationRequest,locationCallBack, Looper.getMainLooper()) }
    }
    fun removeLocationUpdates( activity: AppCompatActivity,callback: LocationCallback){
        val fusedLocationService = LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationService.removeLocationUpdates(callback)
    }
    data class LocationModel(val longitude:Double,val latitude:Double)
}
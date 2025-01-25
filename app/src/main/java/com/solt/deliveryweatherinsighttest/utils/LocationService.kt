package com.solt.deliveryweatherinsighttest.utils

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task

class LocationService {
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
    data class LocationModel(val longitude:Double,val latitude:Double)
}
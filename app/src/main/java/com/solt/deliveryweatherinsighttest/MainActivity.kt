package com.solt.deliveryweatherinsighttest


import android.Manifest
import android.app.Activity
import android.app.ComponentCaller
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.transition.Slide
import android.transition.TransitionManager
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionScene.Transition
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.LocationSettingsStates
import com.solt.deliveryweatherinsighttest.databinding.ActivityMainBinding
import com.solt.deliveryweatherinsighttest.ui.viewmodel.LocationViewModel
import com.solt.deliveryweatherinsighttest.utils.LocationService
import com.solt.deliveryweatherinsighttest.utils.REQUEST_LOCATION_SETTINGS_CHECK
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//View Binding will be used in the project
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val locationViewModel by viewModels<LocationViewModel>()
    //This will be activity launcher contract that we will get from the activity
   lateinit var permissionActivityContractLauncher: ActivityResultLauncher<Array<String>>

lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        //We will need to show a dialog if the use rejects the permission as it is essential for the app
        //And get the user location if the permission is granted
        permissionActivityContractLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){
            val fineLocationGranted = it[Manifest.permission.ACCESS_FINE_LOCATION]
            val coarseLocationGranted = it[Manifest.permission.ACCESS_COARSE_LOCATION]
            if (fineLocationGranted == true && coarseLocationGranted ==true){
                //Show that the permission
                //The location will be gotten when the map is ready
            }
            else{
                //Show the dialog
            }
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
         setContentView(binding.root)

        //Link nav bar to navcontroller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)
      when(requestCode){
          REQUEST_LOCATION_SETTINGS_CHECK ->{
              //When the user is directed to the setting
              when(resultCode){
                  Activity.RESULT_OK->{
                      locationViewModel.didUserChangeLocationSettings.trySend(true)
                  }
                  Activity.RESULT_CANCELED->{
                      locationViewModel.didUserChangeLocationSettings.trySend(false)
                  }
              }
          }
      }
    }



}
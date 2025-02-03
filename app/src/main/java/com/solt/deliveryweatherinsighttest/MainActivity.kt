package com.solt.deliveryweatherinsighttest


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
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionScene.Transition
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

lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
         setContentView(binding.root)
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

  fun showFailureMessage(message: String){
      //This will used to generally show any error message in the app
      binding.infoText.text = message
      binding.infoText.setTextColor(Color.WHITE)
      //Later we will change these
      val gradientDrawable = binding.infoBox.background as?GradientDrawable
      gradientDrawable?.setColor(Color.RED)
      TransitionManager.beginDelayedTransition(binding.root,Slide())
      binding.infoBox.visibility = View.VISIBLE
      //Then a timer will count and when done the info box will be hidden
      val timer = object :CountDownTimer(3000,0){
          override fun onTick(millisUntilFinished: Long) {
              //Nothing to do
          }

          override fun onFinish() {
              TransitionManager.beginDelayedTransition(binding.root,Slide())
           binding.infoBox.visibility = View.GONE
          }

      }
      timer.start()

  }
    fun showSuccessMessage(message: String){
        //This will used to generally show any success message in the app
        binding.infoText.text = message
        binding.infoText.setTextColor(Color.LTGRAY)
        //Later we will change these
        val gradientDrawable = binding.infoBox.background as?GradientDrawable
        gradientDrawable?.setColor(Color.GREEN)
        TransitionManager.beginDelayedTransition(binding.root,Slide())
        binding.infoBox.visibility = View.VISIBLE
        //Then a timer will count and when done the info box will be hidden
        val timer = object :CountDownTimer(3000,0){
            override fun onTick(millisUntilFinished: Long) {
                //Nothing to do
            }

            override fun onFinish() {
                TransitionManager.beginDelayedTransition(binding.root,Slide())
                binding.infoBox.visibility = View.GONE
            }

        }
        timer.start()

    }


}
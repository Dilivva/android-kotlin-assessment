package com.solt.deliveryweatherinsighttest.ui.pages

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.solt.deliveryweatherinsighttest.MainActivity
import com.solt.deliveryweatherinsighttest.data.remote.model.weather.WeatherReportModel
import com.solt.deliveryweatherinsighttest.databinding.MainPageLayoutBinding
import com.solt.deliveryweatherinsighttest.ui.maps.MapTiles
import com.solt.deliveryweatherinsighttest.ui.viewmodel.LocationViewModel
import com.solt.deliveryweatherinsighttest.ui.viewmodel.MainPageViewModel
import com.solt.deliveryweatherinsighttest.utils.LocationService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapLibreMap
import javax.inject.Inject

@AndroidEntryPoint
class MainPage: Fragment() {
lateinit var binding: MainPageLayoutBinding
//This will be gotten when the map is ready
//We will also get the user location
 var map:MapLibreMap? = null
    set(value) {
             field = value
        if (field != null){
            getUserLocation(field!!)
            setOnLongClick(field!!)
        }
    }
    //This will be activity launcher contract that we will get from the activity
lateinit var permissionActivityContractLauncher:ActivityResultLauncher<Array<String>>
//This will be the location service  used for getting the user location and other location related services

    val locationViewModel: LocationViewModel by activityViewModels<LocationViewModel>()
    val mainPageViewModel :MainPageViewModel by viewModels<MainPageViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Initialize the map
        MapLibre.getInstance(requireContext())
        //We will need to show a dialog if the use rejects the permission as it is essential for the app
        //And get the user location if the permission is granted
        permissionActivityContractLauncher = requireActivity().registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
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
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainPageLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //We need to check for if the location permisson
        //If it is not available we need to call the permission launcher
        //If the permission is not still available after the launcher we need to have default location the map will show the location to
        //Check for location permission
        val hasLocationPermissionsBeenGranted =locationViewModel.checkIfLocationPermissionsHaveBeenGranted(requireActivity() as AppCompatActivity)
        if (!hasLocationPermissionsBeenGranted){
            permissionActivityContractLauncher.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION))
        }
        //Set up the map
        binding.mapView.getMapAsync {
            //The location will be gotten when the map is ready
            it.setStyle(MapTiles.BASE_MAP)
            map = it
            it.cameraPosition = CameraPosition.Builder().zoom(14.00).target(LatLng(1.0,2.0)).build()
        }
        //We also need to set up the bottom sheet to show when the user long clicks on the any point in the map and display a weather information
        //That will be set up when the map is ready






    }
    fun getUserLocation(map:MapLibreMap) {
        this.lifecycle.addObserver(MapLocationCallBack(map,this))
    }
    fun updateBottomSheet(weatherReport: WeatherReportModel){
        binding.tempTv.text = "Temperature : ${(weatherReport.main?.temp?:0.0)} K"
        binding.windSpeedTv.text = "Wind Speed ${weatherReport.wind?.speed?:0.0} m/s"
    }
    fun setOnLongClick(map:MapLibreMap){
        map.addOnMapLongClickListener { latLng ->
            //Animate the camera to that position
            map.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            //Then we need to get the weather report for that place
             viewLifecycleOwner.lifecycleScope.launch {

                mainPageViewModel.getWeatherReport(latLng.longitude,latLng.latitude,{
                    //We will update the data in bottom sheet
                    Log.i("Weather",it.main?.temp.toString())
                    updateBottomSheet(it)
                }){
                    Log.i("Weather","Error ${it.message}")
                }
            }

            true
        }

    }
     class MapLocationCallBack( val map: MapLibreMap, val mainPage:MainPage): LocationCallback(),LifecycleEventObserver {
        override fun onLocationResult(location: LocationResult) {
            super.onLocationResult(location)
         //   Log.i("Location","Latitude:${location.lastLocation?.latitude} Longitude : ${location.lastLocation?.longitude}" )
            map.cameraPosition = CameraPosition.Builder().target(LatLng(location.lastLocation?.latitude?:0.0,location.lastLocation?.longitude?:0.0)).build()
        }

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            //Here we will check the lifecycle state of the fragment gto stop location updates if it is on Pause
            when(event){
                Lifecycle.Event.ON_RESUME->{

                   mainPage.locationViewModel.requestLocationUpdates(mainPage,this){(mainPage.requireActivity() as MainActivity).showFailureMessage(it)}
                }
                Lifecycle.Event.ON_PAUSE->{

                    mainPage.locationViewModel.removeLocationUpdates(mainPage,this)
                }
                else -> {}
            }
        }
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }
    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }
}


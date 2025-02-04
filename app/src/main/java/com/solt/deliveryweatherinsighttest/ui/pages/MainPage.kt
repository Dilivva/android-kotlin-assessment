package com.solt.deliveryweatherinsighttest.ui.pages

import android.Manifest
import android.graphics.Color
import android.os.Build
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
import com.google.android.material.snackbar.Snackbar
import com.solt.deliveryweatherinsighttest.MainActivity
import com.solt.deliveryweatherinsighttest.data.remote.model.weather.WeatherReportModel
import com.solt.deliveryweatherinsighttest.databinding.HomeMarkerViewLayoutBinding
import com.solt.deliveryweatherinsighttest.databinding.MainPageLayoutBinding
import com.solt.deliveryweatherinsighttest.ui.maps.CustomMarkerView

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
import org.maplibre.android.plugins.markerview.MarkerView
import org.maplibre.android.plugins.markerview.MarkerViewManager
import javax.inject.Inject
const val LOCATION_HISTORY_ITEM = "location_history_item"
@AndroidEntryPoint
class MainPage: Fragment() {
    lateinit var binding: MainPageLayoutBinding
//This will be gotten when the map is ready
//We will also get the user location
    var map:MapLibreMap? = null
    set(value) {
             field = value
        if (field != null){
            //We will create the marker manager here before use
            markerManager = MarkerViewManager(binding.mapView,field!!)
            getUserLocationUpdate(field!!)
            setOnLongClick(field!!)
            //Then move to the location history camera
            MoveToLocationHistoryIfThere(field!!)
        }
    }

//This will be the location service  used for getting the user location and other location related services

    val locationViewModel: LocationViewModel by activityViewModels<LocationViewModel>()
    val mainPageViewModel :MainPageViewModel by viewModels<MainPageViewModel>()
    //This will be used to attach the markers to the map
    lateinit var markerManager :MarkerViewManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialize the map
        MapLibre.getInstance(requireContext())

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
            //We will get the launcher from the activity
            val activity = requireActivity() as MainActivity

            activity.permissionActivityContractLauncher.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION))
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
    fun getUserLocationUpdate(map:MapLibreMap) {
        this.lifecycle.addObserver(MapLocationCallBack(map,this))
    }
    fun updateBottomSheet(weatherReport: WeatherReportModel){
        binding.nameOfLocation.text = weatherReport.name
        binding.weatherCondition.text = weatherReport.weather?.get(0)?.main?:"No Weather Condition"
        binding.temp.text = "${weatherReport.main?.temp?:0.0}K"
        binding.windSpeed.text = "${weatherReport.wind?.speed}m/s"

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
            //Then add the place to location history
            mainPageViewModel.insertLocationIntoHistory(latLng.latitude,latLng.longitude)

            true
        }

    }
     class MapLocationCallBack( val map: MapLibreMap, val mainPage:MainPage): LocationCallback(),LifecycleEventObserver {
         //We will need one markerview
          var markerView:MarkerView
          val homeMarker :CustomMarkerView
         init {
             val homeView = HomeMarkerViewLayoutBinding.inflate(mainPage.layoutInflater,mainPage.binding.mapView,false).root
            markerView = MarkerView(LatLng(0.0,0.0),homeView)
             homeMarker = CustomMarkerView(0.0,0.0,markerView)
             mainPage.markerManager.addMarker(homeMarker.markerView)
         }
        override fun onLocationResult(location: LocationResult) {
            super.onLocationResult(location)
            map.animateCamera(CameraUpdateFactory.newLatLng(LatLng(location.lastLocation?.latitude?:0.0,location.lastLocation?.longitude?:0.0)))
            //When we get the users location we will use attach a a marker there if there is a longitude and latitude
            val latestLocation = location.lastLocation
            if( latestLocation!= null) {
                Log.i("Location","Latitude : ${latestLocation.latitude}, Longitude: ${latestLocation.longitude}")
                    homeMarker.setLatLng(latestLocation.latitude,latestLocation.longitude)
                }

        }

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            //Here we will check the lifecycle state of the fragment gto stop location updates if it is on Pause
            when(event){
                Lifecycle.Event.ON_RESUME->{

                   mainPage.locationViewModel.requestLocationUpdates(mainPage,this){
                   //Just show a snackbar
                       mainPage.showErrorSnackBar(it)
                    }
                }
                Lifecycle.Event.ON_PAUSE->{

                    mainPage.locationViewModel.removeLocationUpdates(mainPage,this)
                }
                else -> {}
            }
        }
    }

//We need to add a marker view for when a user long clicks on the map
    //We can set there to a drop off or pick up station
    //There will also be a location marker

    //ErrorSnackBar
    fun showErrorSnackBar(errorMessage:String){
        val snackBar = Snackbar.make(binding.root,errorMessage,Snackbar.LENGTH_SHORT)
        snackBar.setTextColor(Color.RED)
        snackBar.show()
    }
    fun showSuccessSnackBar(successMessage:String){
        val snackBar = Snackbar.make(binding.root,successMessage,Snackbar.LENGTH_SHORT)
        snackBar.setTextColor(Color.GREEN)
        snackBar.show()
    }

    //If the user clicks on an location item history it will take him to the map and move the camera to that location
    // so we need to get the location
    fun MoveToLocationHistoryIfThere(map: MapLibreMap){
        val args = arguments
        if(args !=null) {
            val locationHistoryParcel =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    args.getParcelable(LOCATION_HISTORY_ITEM, LatLng::class.java)
                } else {
                    try {


                        args.getParcelable<LatLng>(LOCATION_HISTORY_ITEM)
                    }catch (e:Exception){null}
                }

            //If there is a location history //Then we will update the map with the location history point
            if (locationHistoryParcel != null) map.animateCamera(CameraUpdateFactory.newLatLng(locationHistoryParcel))
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
        markerManager.onDestroy()
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


package com.solt.deliveryweatherinsighttest.ui.pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.solt.deliveryweatherinsighttest.databinding.MainPageLayoutBinding
import com.solt.deliveryweatherinsighttest.ui.maps.MapTiles
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapLibreMap

class MainPage: Fragment() {
lateinit var binding: MainPageLayoutBinding
//This will be gotten when the map is ready
lateinit var map:MapLibreMap

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

        binding.mapView.getMapAsync {
            map = it
            it.setStyle(MapTiles.BASE_MAP)
            it.cameraPosition = CameraPosition.Builder().zoom(14.00).target(LatLng(1.0,2.0)).build()
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


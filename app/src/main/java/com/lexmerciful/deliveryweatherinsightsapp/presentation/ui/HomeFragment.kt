package com.lexmerciful.deliveryweatherinsightsapp.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lexmerciful.deliveryweatherinsightsapp.databinding.FragmentHomeBinding
import com.lexmerciful.deliveryweatherinsightsapp.domain.model.WeatherResponse
import com.lexmerciful.deliveryweatherinsightsapp.presentation.viewmodel.WeatherViewmodel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Error

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val weatherViewmodel by viewModels<WeatherViewmodel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupGetRecommendationButton()
        setupObservers()
    }

    private fun init() {
        binding.pickupLocationInfoLayout.visibility = View.GONE
        binding.dropoffLocationInfoLayout.visibility = View.GONE
        binding.recommendationCardviewLayout.visibility = View.GONE
        binding.errorStateLinearLayout.visibility = View.GONE
    }

    private fun setupGetRecommendationButton() {
        binding.getRecommendationButton.setOnClickListener {
            getWeatherAndRecommendation()
        }
    }

    private fun getWeatherAndRecommendation() {
        val pickUpLocation = binding.pickupEditText.text.toString()
        val dropoffLocation = binding.dropoffEditText.text.toString()

        weatherViewmodel.getWeather(pickUpLocation, dropoffLocation)
    }

    private fun setupObservers() {
        // Observe loading state
        weatherViewmodel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingProgressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observe weather data
        weatherViewmodel.weatherData.observe(viewLifecycleOwner) { (pickupWeatherData, dropoffWeatherData) ->
            displayWeather(pickupWeatherData, dropoffWeatherData)
        }

        // Observe recommendation
        weatherViewmodel.deliveryRecommendation.observe(viewLifecycleOwner) { deliveryRecommendation ->
            binding.recommendationCardviewLayout.visibility = View.GONE
            binding.recommendationValueTextview.text = deliveryRecommendation
        }

        // Error state
        weatherViewmodel.errorState.observe(viewLifecycleOwner) { error ->
            setupErrorState(error)
        }
    }

    private fun displayWeather(pickup: WeatherResponse, dropoff: WeatherResponse) {
        //Pickup
        binding.pickupLocationInfoLayout.visibility = View.VISIBLE
        binding.weatherDataValueTextview.text = pickup.weather[0].main
        binding.pickupTemperatureDegreeTextview.text = pickup.main.temp.toString() + "°C"
        binding.windspeedTextview.text = pickup.wind.speed.toString()

        //Drop-off
        binding.dropoffLocationInfoLayout.visibility = View.VISIBLE
        binding.dropoffWeatherDataValueTextview.text = dropoff.weather[0].main
        binding.dropoffTemperatureDegreeTextview.text = dropoff.main.temp.toString() + "°C"
        binding.dropoffWindspeedTextview.text = dropoff.wind.speed.toString()
    }

    private fun setupErrorState(errorText: String?) {
        if (!errorText.isNullOrEmpty()) {
            binding.errorStateLinearLayout.visibility = View.VISIBLE
            binding.errorTextview.text = errorText
        } else {
            binding.errorStateLinearLayout.visibility = View.GONE
        }

    }

    companion object {
        private val TAG = HomeFragment::class.simpleName
    }

}
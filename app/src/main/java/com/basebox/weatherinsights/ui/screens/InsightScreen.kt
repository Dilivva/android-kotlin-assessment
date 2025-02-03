package com.basebox.weatherinsights.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.basebox.weatherinsights.data.db.WeatherData
import com.basebox.weatherinsights.data.model.InsightResponse
import com.basebox.weatherinsights.ui.viewmodel.InsightViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightScreen(viewModel: InsightViewModel, nav: NavController) {

    val pickupLocation = remember { mutableStateOf("") }
    val dropoffLocation = remember { mutableStateOf("") }
    val weather = viewModel.weatherData.observeAsState().value
    val dropoffWeather = viewModel.weatherDropOffData.observeAsState().value

    val expandedPickup = remember { mutableStateOf(false) }
    val expandedDropoff = remember { mutableStateOf(false) }
    var currentLocation = ""
    var dropoffLocationData = ""
    var recommendation = ""
    var weatherResponse: InsightResponse? = null

    // Dummy data for dropdown options
    val locations = listOf("New York", "Los Angeles", "Chicago", "Miami", "Lagos", "Abuja",
        "Accra"," Cairo", "Nairobi")

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {

        // Pickup location dropdown
        ExposedDropdownMenuBox(
            expanded = expandedPickup.value,
            onExpandedChange = { expandedPickup.value = it }) {
            OutlinedTextField(
                value = pickupLocation.value,
                onValueChange = { },
                label = { Text("Pickup Location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            )
            currentLocation = pickupLocation.value
            ExposedDropdownMenu(
                expanded = expandedPickup.value,
                onDismissRequest = { expandedPickup.value = false }) {
                locations.forEach { location ->
                    DropdownMenuItem(text = {
                        Text(location)
                    }, onClick = {
                        pickupLocation.value = location
                        expandedPickup.value = false
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dropoff location dropdown
        ExposedDropdownMenuBox(
            expanded = expandedDropoff.value,
            onExpandedChange = { expandedDropoff.value = it }) {
            OutlinedTextField(
                value = dropoffLocation.value,
                onValueChange = { },
                label = { Text("Dropoff Location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            )
            dropoffLocationData = dropoffLocation.value
            ExposedDropdownMenu(
                expanded = expandedDropoff.value,
                onDismissRequest = { expandedDropoff.value = false }) {
                locations.forEach { location ->
                    DropdownMenuItem(text = { Text(location) }, onClick = {
                        dropoffLocation.value = location
                        expandedDropoff.value = false
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Fetch weather button
        if (!currentLocation.isNullOrEmpty() && !dropoffLocationData.isNullOrEmpty()) {
        Button(
            onClick = { viewModel.fetchData(pickupLocation.value, dropoffLocation.value) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Get Weather Insight", fontWeight = FontWeight.Bold)
        }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Display weather results if available
        weather?.let {
            dropoffWeather?.let {
//            Log.d("InsightScreen", "Weather Data: $it")
                weatherResponse = it

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Today's Weather Insight: ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        "Pick up Weather Info: ${weather.weather.first().description}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        " Pick up Temperature: ${weather.main.temp} C",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        "Drop off Weather Info: ${dropoffWeather.weather.first().description}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        "Drop off Temperature: ${dropoffWeather.main.temp} C",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        "Recommendation",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineMedium
                    )

                    if (isFavorable(weather.weather.first().description) &&
                        isFavorable(dropoffWeather.weather.first().description)
                    ) {
                        Text(
                            "Proceed with Delivery",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    } else {
                        Text(
                            "Advised to hold off until clear weather",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }


                    Spacer(modifier = Modifier.height(24.dp))

                    // Navigate to saved locations screen button
                    if (!currentLocation.isNullOrEmpty()) {
                        Button(
                            onClick = {
                                nav.navigate("SavedLocationsScreen").also {
                                    viewModel.saveData(
                                        currentLocation,
                                        weatherResponse?.main!!.temp,
                                        weatherResponse?.weather!!.first().description
                                    )
                                }
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("View Saved Locations", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

}
fun isFavorable(condition: String): Boolean {
    return when (condition) {
        "clear sky", "few clouds", "scattered clouds" -> true
        else -> false
    }
}

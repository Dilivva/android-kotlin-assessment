package com.basebox.weatherinsights.ui.screens

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
import androidx.navigation.compose.rememberNavController
import com.basebox.weatherinsights.ui.viewmodel.InsightViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightScreen(viewModel: InsightViewModel, nav: NavController) {

    var pickupLocation = remember { mutableStateOf("") }
    var dropoffLocation = remember { mutableStateOf("") }
    val weather = viewModel.weatherData.observeAsState().value
//    var expanded = remember { mutableStateOf(false)

    var expandedPickup = remember { mutableStateOf(false) }
    var expandedDropoff = remember { mutableStateOf(false) }


    // Dummy data for dropdown options
    val locations = listOf("New York", "Los Angeles", "Chicago", "Miami", "Lagos", "Abuja",
        "Accra"," Cairo", "Nairobi")

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {

//        var expandedPickup = remember { mutableStateOf(false) }
        // Pickup location dropdown
        ExposedDropdownMenuBox(expanded = expandedPickup.value, onExpandedChange = { expandedPickup.value = it }) {
            OutlinedTextField(
                value = "${pickupLocation.value}",
                onValueChange = {  },
                label = { Text("Pickup Location") },
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                readOnly = true,
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            )
            ExposedDropdownMenu(expanded = expandedPickup.value, onDismissRequest = { expandedPickup.value = false }) {
                locations.forEach { location ->
                    DropdownMenuItem(text = {
                        Text(location) }, onClick = { pickupLocation.value = location
                        expandedPickup.value = false  })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dropoff location dropdown
        ExposedDropdownMenuBox(expanded = expandedDropoff.value, onExpandedChange = { expandedDropoff.value = it }) {
            OutlinedTextField(
                value = "${dropoffLocation.value}",
                onValueChange = { },
                label = { Text("Dropoff Location") },
                modifier = Modifier.fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            )
            ExposedDropdownMenu(expanded = expandedDropoff.value, onDismissRequest = { expandedDropoff.value = false }) {
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
        Button(
            onClick = { viewModel.fetchData(pickupLocation.value, dropoffLocation.value) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Get Weather Insight", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Display weather results if available
        weather?.let {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                viewModel.saveData(locations.random(), it.main.temp, it.weather.first().description)
                Text("Today's Weather Insight: ", fontWeight = FontWeight.Bold, fontSize = 24.sp, modifier = Modifier.align(Alignment.CenterHorizontally), style = MaterialTheme.typography.headlineSmall)
                Text("${it.weather.first().description}", fontWeight = FontWeight.Bold, fontSize = 24.sp, modifier = Modifier.align(Alignment.CenterHorizontally), style = MaterialTheme.typography.headlineSmall)
                Text("Temperature: ${it.main.temp}°C", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterHorizontally), style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(24.dp))

                // Navigate to saved locations screen button
                Button(
                    onClick = { nav.navigate("SavedLocationsScreen")},
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("View Saved Locations", fontWeight = FontWeight.Bold)
                }
            }
        }
    }

//    var location = remember { mutableStateOf("") }
//    val weather = viewModel.weatherData.observeAsState().value
//
//    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        TextField(value = location.value, onValueChange = { location.value = it })
//        Button(onClick = { viewModel.fetchData(location.value) }) {
//            Text("Get Weather")
//        }
//
//        weather?.let {
//            Text("Temperature: ${it.main.temp}°C")
//            Text("Condition: ${it.weather.first().description}")
//        }
//    }
}

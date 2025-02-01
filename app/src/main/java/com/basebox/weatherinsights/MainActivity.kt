package com.basebox.weatherinsights

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.basebox.weatherinsights.ui.screens.InsightScreen
import com.basebox.weatherinsights.ui.screens.SavedLocationsScreen
import com.basebox.weatherinsights.ui.theme.WeatherInsightsTheme
import com.basebox.weatherinsights.ui.viewmodel.InsightViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherInsightsTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController, startDestination = "insightScreen", Modifier.padding(innerPadding)) {
                        composable("insightScreen") {
                            val viewModel: InsightViewModel = hiltViewModel()
                            InsightScreen(viewModel = viewModel, navController)
                        }
                        composable("savedLocationsScreen") {
                            val viewModel: InsightViewModel = hiltViewModel()
                            SavedLocationsScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherInsightsTheme {
        Greeting("Android")
    }
}
package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.ui.theme.WeatherAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                WeatherApp()
            }
        }
    }
}

@Composable
fun CityWeatherItem(cityWeather: CityWeather) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(75.dp)
        ) {
            Text(text = "City: ${cityWeather.city}")
            Text(text = "Region: ${cityWeather.region}")
            Text(text = "Country: ${cityWeather.country}")
            Text(text = "Local Time: ${cityWeather.localtime}")
            Text(text = "Temperature: ${cityWeather.temperature}${cityWeather.unit}")
            Text(text = "Condition: ${cityWeather.conditionText}")
            Text(text = "Wind: ${cityWeather.windKph} kph at ${cityWeather.windDegree}°")
            Text(text = "Humidity: ${cityWeather.humidity}%")
        }
    }
}

@Composable
fun CityWeatherList(cityWeathers: MutableList<CityWeather>) {
    LazyColumn {
        items(cityWeathers) { cityWeather ->
            CityWeatherItem(cityWeather)
        }
    }
}

@Composable
fun WeatherApp() {
    val cityWeathers = remember { mutableStateListOf<CityWeather>() }
    var enteredCity by remember { mutableStateOf("") }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = enteredCity,
                onValueChange = { enteredCity = it },
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    if (enteredCity.isNotEmpty()) {
                        CoroutineScope(Dispatchers.IO).launch {
                            val cityWeather = fetchWeather(enteredCity)
                            cityWeather?.let {
                                cityWeathers.add(it)
                            }
                        }
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Add")
            }
        }
        CityWeatherList(cityWeathers)
    }
}

@Preview
@Composable
fun WeatherAppPreview() {
    WeatherApp()
}

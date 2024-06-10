// CityWeather.kt
package com.example.weatherapp

data class CityWeather(
    val city: String,
    val region: String,
    val country: String,
    val localtime: String,
    val temperature: Double,
    val conditionText: String,
    val windKph: Double,
    val windDegree: Int,
    val humidity: Int,
    val unit: String = "°C"
)

data class WeatherResponse(
    val location: Location,
    val current: Current
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val localtime: String
)

data class Current(
    val temp_c: Double,
    val condition: Condition,
    val wind_kph: Double,
    val wind_degree: Int,
    val humidity: Int
)

data class Condition(
    val text: String,
)

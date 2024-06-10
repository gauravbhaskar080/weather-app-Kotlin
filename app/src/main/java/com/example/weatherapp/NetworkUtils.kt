// NetworkUtils.kt
package com.example.weatherapp

import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson

fun fetchWeather(city: String): CityWeather? {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://weatherapi-com.p.rapidapi.com/current.json?q=$city")
        .get()
        .addHeader("x-rapidapi-key", "82f428cf82mshd4cd3a8e69a1cd3p191a1fjsnbe543430f34f")
        .addHeader("x-rapidapi-host", "weatherapi-com.p.rapidapi.com")
        .build()

    return client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) return@use null

        val body = response.body?.string() ?: return@use null
        val weatherResponse = Gson().fromJson(body, WeatherResponse::class.java)
        CityWeather(
            city = weatherResponse.location.name,
            region = weatherResponse.location.region,
            country = weatherResponse.location.country,
            localtime = weatherResponse.location.localtime,
            temperature = weatherResponse.current.temp_c,
            conditionText = weatherResponse.current.condition.text,
            windKph = weatherResponse.current.wind_kph,
            windDegree = weatherResponse.current.wind_degree,
            humidity = weatherResponse.current.humidity
        )
    }
}

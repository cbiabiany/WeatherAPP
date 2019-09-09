package com.biabiany.weatherapp2.model

import com.squareup.moshi.Json

data class CurrentWeather (
    @Json(name = "coord") var coord: Coordinates,
    @Json(name = "weather") var weather: Array<WeatherInfo>,
    @Json(name = "base") var base: String,
    @Json(name = "main") var main: WeatherMetrics,
    @Json(name = "visibility") var visibility: Int,
    @Json(name = "wind") var wind: WindMetrics,
    @Json(name = "dt") var dt: Long,
    @Json(name = "sys") var sys: Sys,
    @Json(name = "name") var name: String,
    @Json(name = "cod") var cod: Int
)

data class Sys (
    @Json(name = "type") var type: Int,
    @Json(name = "id") var id: Int,
    @Json(name = "message") var message: Float,
    @Json(name = "country") var country: String,
    @Json(name = "sunrise") var sunrise: Long,
    @Json(name = "sunset") var sunset: Long
)

data class WindMetrics (
    @Json(name = "speed") var speed: Float,
    @Json(name = "deg") var deg: Float
)

data class WeatherMetrics (
    @Json(name = "temp") var temp: Float,
    @Json(name = "pressure") var pressure: Int,
    @Json(name = "humidity") var humidity: Int,
    @Json(name = "temp_min") var temp_min: Float,
    @Json(name = "temp_max") var temp_max: Float
)

data class WeatherInfo (
    @Json(name = "id") var id: Int,
    @Json(name = "main") var main: String,
    @Json(name = "description") var description: String,
    @Json(name = "icon") var icon: String
)

data class Coordinates (
    @Json(name = "lon") var lon: Float,
    @Json(name = "lat") var lat: Float
)
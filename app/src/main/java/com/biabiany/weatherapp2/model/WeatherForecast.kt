package com.biabiany.weatherapp2.model

import com.squareup.moshi.Json

data class WeatherForecast (
    @Json(name = "cod") var cod: String,
    @Json(name = "message") var message: Float,
    @Json(name = "cnt") var cnt: Int,
    @Json(name = "list") var list: Array<Forecast>,
    @Json(name = "city") var city: City
)

data class Forecast (
    @Json(name = "dt") var dt: Long,
    @Json(name = "main") var main: ForecastMetrics,
    @Json(name = "weather") var weather: Array<WeatherInfo>,
    @Json(name = "clouds") var clouds: Clouds,
    @Json(name = "wind") var wind: WindMetrics,
    @Json(name = "sys") var sys: SysInfo,
    @Json(name = "dt_txt") var dt_txt: String
)

data class ForecastMetrics (
    @Json(name = "temp") var temp: Float,
    @Json(name = "temp_min") var temp_min: Float,
    @Json(name = "temp_max") var temp_max: Float,
    @Json(name = "pressure") var pressure: Float,
    @Json(name = "sea_level") var sea_level: Float,
    @Json(name = "grnd_level") var grnd_level: Float,
    @Json(name = "humidity") var humidity: Int,
    @Json(name = "temp_kf") var temp_kf: Float

)
data class SysInfo (
    @Json(name = "pod") var pod: String
)

data class Clouds (
    @Json(name = "all") var all: Int
)

data class City (
    @Json(name = "id") var id: Int,
    @Json(name = "name") var name: String,
    @Json(name = "coord") var coord: Coordinates,
    @Json(name = "country") var country: String,
    @Json(name = "timezone") var timezone: Int,
    @Json(name = "sunrise") var sunrise: Long,
    @Json(name = "sunset") var sunset: Long
)
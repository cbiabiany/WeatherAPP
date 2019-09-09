package com.biabiany.weatherapp2

import com.biabiany.weatherapp2.model.CurrentWeather
import com.biabiany.weatherapp2.model.WeatherForecast
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    companion object {
        val APP_ID: String = "7fb8d05833caa8ccb05fd1ca3bc3878c"
        val API_BASE_URL: String = "https://api.openweathermap.org/data/2.5/"
        val ICON_URL_PREFIX: String = "https://openweathermap.org/img/wn/"
        val ICON_URL_SUFFIX: String = "@2x.png"
        val CITY_ID: Int = 2988507
    }

    /**
     * Enum used to tell if we use metric unit system (Celcius) or imperial system (Fahrenheit)
     */
    enum class Units {

        METRIC,

        IMPERIAL,
    }

    @GET("weather")
    fun getCurrentWeather(
        @Query("id") cityId: Int,
        @Query("cnt") count: Int,
        @Query("appid") appid: String,
        @Query("units") units: String
    ) : Flowable<CurrentWeather>

    @GET("forecast")
    fun getWeatherForecastbyCity(
        @Query("id") cityId: Int,
        @Query("appid") appid: String,
        @Query("units") units: String
    ) : Flowable<WeatherForecast>

    @GET("forecast")
    fun getWeatherForecastbyLocation(
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?,
        @Query("appid") appid: String,
        @Query("units") units: String
    ) : Flowable<WeatherForecast>
}
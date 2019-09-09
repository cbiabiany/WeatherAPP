package com.biabiany.weatherapp2

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.biabiany.weatherapp2.model.WeatherForecast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject

class WeatherViewModel : ViewModel() {
    companion object {
        val LOG = Logger.getLogger(WeatherViewModel::class.java.name)
    }

    @Inject lateinit var service: WeatherService

    //private var currentWeather: MutableLiveData<CurrentWeather> = MutableLiveData() (not used)

    private var weatherForecast: MutableLiveData<WeatherForecast> = MutableLiveData()

    private var wvmInjector: WeatherViewModelInjector = DaggerWeatherViewModelInjector.builder()
                                                            .networkModule(NetworkModule(WeatherService.API_BASE_URL))
                                                            .build()

    init {
        wvmInjector.inject(this)
    }

    fun getWeatherForecast(): MutableLiveData<WeatherForecast> {
        return weatherForecast
    }

    // Retrieve current weather from network (not used)
    //private fun loadCurrentWeather() {
    //    LOG.log(Level.INFO,"Load current weather forecast from network ...")
    //    service.getCurrentWeather(WeatherService.CITY_ID, 7, WeatherService.APP_ID, WeatherService.Units.METRIC.name)
    //        .observeOn(AndroidSchedulers.mainThread())
    //        .subscribeOn(Schedulers.io())
    //        .subscribe(object : DisposableSubscriber<CurrentWeather>() {
    //            override fun onComplete() {
    //                LOG.log(Level.INFO,"Request completed !")
    //            }
    //
    //            override fun onNext(t: CurrentWeather?) {
    //                LOG.log(Level.INFO,"Request succeeded : $t")
    //                currentWeather.setValue(t)
    //            }
    //
    //            override fun onError(t: Throwable?) {
    //                LOG.log(Level.SEVERE,"Request failed.")
    //            }
    //        })
    //}

    // Retrieve five day weather forecast from network (not used)
    //fun loadWeatherForecastbyCity() {
    //    LOG.log(Level.INFO, "Load 5 days weather forecast from network")
    //    service.getWeatherForecastbyCity(WeatherService.CITY_ID, WeatherService.APP_ID, WeatherService.Units.METRIC.name)
    //        .observeOn(AndroidSchedulers.mainThread())
    //        .subscribeOn(Schedulers.io())
    //        .subscribe(object: DisposableSubscriber<WeatherForecast>() {
    //            override fun onComplete() {
    //                LOG.log(Level.INFO,"Request completed !")
    //            }
    //
    //            override fun onNext(t: WeatherForecast?) {
    //                LOG.log(Level.INFO,"Request succeeded : $t")
    //                weatherForecast.setValue(t)
    //            }
    //
    //            override fun onError(t: Throwable?) {
    //                LOG.log(Level.SEVERE,"Request failed.")
    //            }
    //        })
    //}

    // Retrieve five day weather forecast from network
    fun loadWeatherForecastbyLocation(loc: Location?) {
        LOG.log(Level.INFO, "Load 5 days weather forecast from network")
        service.getWeatherForecastbyLocation(loc?.latitude, loc?.longitude, WeatherService.APP_ID, WeatherService.Units.METRIC.name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object: DisposableSubscriber<WeatherForecast>() {
                override fun onComplete() {
                    LOG.log(Level.INFO,"Request completed !")
                }

                override fun onNext(t: WeatherForecast?) {
                    LOG.log(Level.INFO,"Request succeeded : $t")
                    weatherForecast.setValue(t)
                }

                override fun onError(t: Throwable?) {
                    LOG.log(Level.SEVERE,"Request failed.")
                }
            })
    }
}
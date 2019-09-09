package com.biabiany.weatherapp2

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.biabiany.weatherapp2.model.WeatherForecast
import com.biabiany.weatherapp2.ui.WeatherAdapter
import java.util.logging.Level
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    companion object {
        val LOG = Logger.getLogger(NetworkModule::class.java.name)
    }

    private val RC_LOCATION_PERMISSION = 1

    private var vm: WeatherViewModel? = null
    private var weatherView: RecyclerView? = null
    private var locationManager: LocationManager? = null

    private lateinit var wfObserver: Observer<WeatherForecast>

    private var locationListener = object : LocationListener {
        override fun onLocationChanged(p0: Location?) {
            vm?.loadWeatherForecastbyLocation(p0)
        }

        override fun onProviderDisabled(p0: String?) {
            // nothing
        }

        override fun onProviderEnabled(p0: String?) {
            // nothing
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            // nothing
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        vm = ViewModelProviders.of(this)[WeatherViewModel::class.java]

        var viewManager = LinearLayoutManager(this)

        wfObserver = Observer {
            weatherView = findViewById(R.id.weather_list)
            weatherView?.apply {

                setHasFixedSize(true)

                adapter = WeatherAdapter(it.list)

                layoutManager = viewManager
            }
        }

        // I think there is a bug in the Android library here. I used to be able to use observer()
        // method but now I have to use observeForever because observer method won't accept my Activity
        // context.
        vm?.getWeatherForecast()?.observeForever(wfObserver)

        // Request Location persmission
        if(requestLocationPermission()) {
            requestLocationUpdate()
        }
    }

    private fun requestLocationUpdate() {
        LOG.log(Level.INFO, "requestLocationUpdate")

        var isLocationEnabled: Boolean = locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) as Boolean

        if (isLocationEnabled) {

            // request location update
            locationManager?.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0,
                0.0f,
                locationListener
            )

            // Update weather forecast
            locationListener.onLocationChanged(locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER))
        } else {
            LOG.log(Level.SEVERE, "GPS is not activated")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            RC_LOCATION_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocationUpdate()
                }
            }
        }
    }

    private fun requestLocationPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LOG.log(Level.INFO, "requestLocationPermission")
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), RC_LOCATION_PERMISSION)
                return false
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        vm?.getWeatherForecast()?.removeObserver(wfObserver)
    }
}

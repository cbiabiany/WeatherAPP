package com.biabiany.weatherapp2.ui

import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.biabiany.weatherapp2.R
import com.biabiany.weatherapp2.WeatherService
import com.biabiany.weatherapp2.model.Forecast
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class WeatherAdapter (var weatherList: Array<Forecast>) : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {

    class WeatherHolder (view: View) : RecyclerView.ViewHolder(view) {

        var weatherIcon: ImageView

        var temperature: TextView

        var time: TextView

        init {
            weatherIcon = view.findViewById(R.id.weather_icon)
            temperature = view.findViewById(R.id.weather_temperature)
            time = view.findViewById(R.id.weather_time)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.weather_cardview, parent, false)
        return WeatherHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {

        // Set weather icon
        Picasso.get().load(WeatherService.ICON_URL_PREFIX
                + weatherList.get(position).weather[0].icon
                + WeatherService.ICON_URL_SUFFIX).into(holder.weatherIcon)

        // Set temperature
        holder.temperature.text = weatherList.get(position).main.temp.roundToInt().toString() + "°C"

        // Set time
        var df = SimpleDateFormat()
        var cl = Calendar.getInstance()
        cl.timeInMillis = weatherList.get(position).dt * 1000
        holder.time.text = df.format(cl.timeInMillis)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }
}
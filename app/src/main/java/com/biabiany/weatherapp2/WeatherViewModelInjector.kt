package com.biabiany.weatherapp2

import dagger.Component

@Component(modules = [NetworkModule::class])
interface WeatherViewModelInjector {

    fun inject(wvm: WeatherViewModel)

}
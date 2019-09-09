package com.biabiany.weatherapp2

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.logging.Level
import java.util.logging.Logger

@Module
class NetworkModule (url: String) {
    companion object {
        val LOG = Logger.getLogger(NetworkModule::class.java.name)
    }

    private val baseUrl: String = url

    @Provides
    fun providesBaseUrl(): String {
        LOG.log(Level.INFO,"Provide base url : $baseUrl ...")
        return baseUrl
    }

    @Provides
    fun providesNetworkModule(url: String): Retrofit {
        LOG.log(Level.INFO,"Provide network module with base url $url ...")

        var logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        var httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build()
    }

    @Provides
    fun providesWeatherService(retrofit: Retrofit) : WeatherService {
        LOG.log(Level.INFO,"Provide weather service ...")
        return retrofit.create(WeatherService::class.java)
    }
}
package com.example.astux7.weatheralert.data

import android.content.Context
import com.example.astux7.weatheralert.model.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by astux7 on 21/09/2017.
 */
class ForecastNetworkClient(val context:Context) {
    val apiKey = FORECAST_KEY

    fun getForecastBy(city: String): Call<WeatherForecast> {
        val network = Retrofit.Builder()
                .baseUrl(FORECAST_CITY_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val forecastService = network.create(ForecastService::class.java)
        val forecast = forecastService.forecastByLocation("imperial", city, apiKey)
        return forecast
    }
}
package com.example.astux7.weatheralert.model

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by astux7 on 21/09/2017.
 */
interface ForecastService {
    @GET("data/2.5/weather")
    fun forecastByLocation(@Query("units") units: String, @Query("q") location: String, @Query("APPID") apiKey: String): retrofit2.Call<WeatherForecast>
}
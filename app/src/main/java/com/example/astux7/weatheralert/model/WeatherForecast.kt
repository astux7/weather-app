package com.example.astux7.weatheralert.model

/**
 * Created by astux7 on 25/09/2017.
 */
class WeatherForecast {
    var wind: Wind = Wind()
}

class Wind {
    var speed: Float = 0.0f
    var deg: Int = 0
}
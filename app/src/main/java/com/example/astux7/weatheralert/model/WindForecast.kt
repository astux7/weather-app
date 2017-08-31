package com.example.astux7.weatheralert.model

/**
 * Created by abe29 on 30/08/2017.
 */
class WindForecast() {
    var location:Location? = null
    var speed:String? = null
    var direction:String? = null

    constructor(location: Location, speed: String, direction: String) : this() {
        this.location = location
        this.speed = speed
        this.direction = direction
    }

    fun formatSpeed(): String {
        return this.speed + " m/s"
    }

    fun formatDirection(): String {
        return this.direction + " deg"
    }


}
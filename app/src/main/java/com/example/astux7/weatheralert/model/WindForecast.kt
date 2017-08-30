package com.example.astux7.weatheralert.model

/**
 * Created by abe29 on 30/08/2017.
 */
class WindForecast {
    var location:Location? = null
    var speed:String? = null
    var direction:String? = null

    constructor(location: Location, speed: String, direction: String) {
        this.location = location
        this.speed = speed + " mph"
        this.direction = direction + " deg"
    }
}
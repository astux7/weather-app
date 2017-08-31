package com.example.astux7.weatheralert.model

/**
 * Created by astux7 on 25/08/2017.
 */
class Location() {
    var id: Int? = null
    var city: String? = null

    constructor(id: Int, city: String): this() {
        this.id = id
        this.city = city
    }
}
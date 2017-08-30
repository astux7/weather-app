package com.example.astux7.weatheralert.data

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.example.astux7.weatheralert.model.FORECAST_API
import com.example.astux7.weatheralert.model.FORECAST_KEY
import org.json.JSONException

/**
 * Created by abe29 on 30/08/2017.
 */
class ForecastService {
    val volleyRequest: RequestQueue? = null
    var location:String? = null

    constructor(location: String){
        this.location = location
    }

    fun currentWindForecast(){
       //  volleyRequest = Volley.newRequestQueue(this)
        // var forecastService = ForecastService(location)
        //  forecastService.currentWindForecast()

        var apiURL = FORECAST_API + location + "&APPID=" + FORECAST_KEY
        val dataReq = StringRequest(Request.Method.GET, apiURL,
                Response.Listener {
                    response: String? ->
                    try {

                    }catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener {
                    error: VolleyError? ->
                    try {

                    }catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
        )

        volleyRequest!!.add(dataReq)
    }

}
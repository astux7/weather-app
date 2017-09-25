package com.example.astux7.weatheralert.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.astux7.weatheralert.R
import com.example.astux7.weatheralert.data.ForecastNetworkClient

import com.example.astux7.weatheralert.data.LocationDatabaseHandler
import com.example.astux7.weatheralert.model.*

import kotlinx.android.synthetic.main.activity_current_forecast.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback


class CurrentForecast : AppCompatActivity() {
    var dbHandler: LocationDatabaseHandler? = null
    var location: Location? = null
    var windForecast: WindForecast? = null
    var volleyRequest: RequestQueue? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_forecast)
        location = Location()
        val city = intent.extras!!.get("Location")
        location!!.city = city.toString()
        getForecast(location!!)

        buttonAddToFav.setOnClickListener {
            dbHandler = LocationDatabaseHandler(this)
            saveToDb(location!!)
            Toast.makeText(this, location!!.city + " saved", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, FavLocations::class.java))
            finish()
        }

        backButton.setOnClickListener {
            startActivity(Intent(this, FavLocations::class.java))
            finish()
        }
    }

    private fun saveToDb(location: Location){
        dbHandler!!.createLocation(location)
    }

    private fun getForecast(location: Location) {
        val network = ForecastNetworkClient(applicationContext)
        val call = network.getForecastByCity(location)
        call.enqueue(object: Callback<WeatherForecast> {
            override fun onResponse(call: Call<WeatherForecast>?, response: retrofit2.Response<WeatherForecast>?) {
                if(response != null && response.body() != null) {
                    var windForecast: WeatherForecast? = response.body()
                    tvLocationName.text = location.city
                    tvSpeed.text = windForecast?.wind?.speed.toString()
                    tvDirection.text = windForecast?.wind?.deg.toString()
                }
            }

            override fun onFailure(call: Call<WeatherForecast>?, t: Throwable?) {
                Toast.makeText(null, "Problem getting forecast for " + location, Toast.LENGTH_LONG).show()
                //startActivity(Intent(this, AddLocation::class.java))
            }

        })
    }

//    private fun getForecast(location: Location) {
//        // JSON data
//        volleyRequest = Volley.newRequestQueue(this)
//        val url = FORECAST_API + location.city +  "&appid=" + FORECAST_KEY
//        val forecastRequest = JsonObjectRequest(Request.Method.GET, url,
//                Response.Listener {
//                    response: JSONObject ->
//                    try {
//                        val speed = response.getJSONObject("wind")["speed"].toString()
//                        val direction = response.getJSONObject("wind")["deg"].toString()
//                        windForecast = WindForecast(location, speed, direction)
//                        tvLocationName.text = windForecast!!.location!!.city
//                        tvSpeed.text = windForecast!!.formatSpeed()
//                        tvDirection.text = windForecast!!.formatDirection()
//                    }catch (e: JSONException) { e.printStackTrace() }
//                },
//                Response.ErrorListener {
//                    _: VolleyError ->
//                    try {
////                        Toast.makeText(null,
////                                error.message + location.name,
////                                Toast.LENGTH_LONG).show()
//
//                        startActivity(Intent(this, AddLocation::class.java))
//                    }catch (e: JSONException) { e.printStackTrace() }
//                }
//        )
//        volleyRequest!!.add(forecastRequest)
//
//    }

}



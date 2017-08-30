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
import com.example.astux7.weatheralert.data.ForecastDatabaseHandler
import com.example.astux7.weatheralert.model.FORECAST_API
import com.example.astux7.weatheralert.model.FORECAST_KEY
import com.example.astux7.weatheralert.model.Location
import com.example.astux7.weatheralert.model.WindForecast
import kotlinx.android.synthetic.main.activity_current_forecast.*
import org.json.JSONException
import org.json.JSONObject



class CurrentForecast : AppCompatActivity() {
    var dbHandler: ForecastDatabaseHandler? = null
    var location: Location? = null
    var windForecast: WindForecast? = null
    var volleyRequest: RequestQueue? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_forecast)
        location = Location()
        var name = intent.extras!!.get("Location")
        location!!.name = name.toString()
        getForecast(location!!)

        buttonAddToFav.setOnClickListener {
            dbHandler = ForecastDatabaseHandler(this)
            saveToDb(location!!)
            Toast.makeText(this, location!!.name + " saved", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


    fun saveToDb(location: Location){
        dbHandler!!.createLocation(location)
    }

    private fun getForecast(location: Location) {
        // JSON data
        volleyRequest = Volley.newRequestQueue(this)
        val url = FORECAST_API + location.name +  "&appid=" + FORECAST_KEY
        val forecastRequest = JsonObjectRequest(Request.Method.GET, url,
                Response.Listener {
                    response: JSONObject ->
                    try {
                        val speed = response.getJSONObject("wind")["speed"].toString()
                        val direction = response.getJSONObject("wind")["deg"].toString()
                        windForecast = WindForecast(location, speed, direction)
                        tvLocationName.text = windForecast!!.location!!.name
                        tvSpeed.text = windForecast!!.speed.toString()
                        tvDirection.text = windForecast!!.direction.toString()
                    }catch (e: JSONException) { e.printStackTrace() }
                },
                Response.ErrorListener {
                    error: VolleyError ->
                    try {
//                        Toast.makeText(null,
//                                error.message + location.name,
//                                Toast.LENGTH_LONG).show()

                        startActivity(Intent(this, AddLocation::class.java))
                    }catch (e: JSONException) { e.printStackTrace() }
                }
        )
        volleyRequest!!.add(forecastRequest)

    }

}



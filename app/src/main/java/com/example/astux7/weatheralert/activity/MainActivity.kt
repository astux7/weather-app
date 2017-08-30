package com.example.astux7.weatheralert.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.astux7.weatheralert.R
import com.example.astux7.weatheralert.data.ForecastDatabaseHandler
import com.example.astux7.weatheralert.data.LocationListAdapter
import com.example.astux7.weatheralert.model.FORECAST_API
import com.example.astux7.weatheralert.model.FORECAST_KEY
import com.example.astux7.weatheralert.model.Location
import com.example.astux7.weatheralert.model.WindForecast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var dbHandler: ForecastDatabaseHandler? = null
    var volleyRequest: RequestQueue? = null
    private var adapter: LocationListAdapter? = null
    private var windForecastList: ArrayList<WindForecast>? = null
    private var favLocationList: ArrayList<Location>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFavLocationForecast()
    }

    private fun loadFavLocationForecast() {
        // DB data
        dbHandler = ForecastDatabaseHandler(this)
        if(dbHandler!!.getLocationCount() > 0) {
            // load locations - and weather
            favLocationList = dbHandler!!.readLocations()
            for(location in favLocationList!!.iterator()) {
                getForecast(location)
            }
        } else {
            startActivity(Intent(this, AddLocation::class.java ))
        }
    }

    private fun getForecast(location: Location) {
        // JSON data
        volleyRequest = Volley.newRequestQueue(this)
        windForecastList = ArrayList<WindForecast>()
        layoutManager = LinearLayoutManager(this)
        adapter = LocationListAdapter(windForecastList!!, this)
        // set up list
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        val url = FORECAST_API + location.name +  "&appid=" + FORECAST_KEY
        val forecastRequest = JsonObjectRequest(Request.Method.GET, url,
                Response.Listener {
                    response: JSONObject ->
                    try {
                        val speed = response.getJSONObject("wind")["speed"].toString()
                        val direction = response.getJSONObject("wind")["deg"].toString()
                        val forecast = WindForecast(location, speed, direction)
                        windForecastList!!.add(forecast)
                        adapter!!.notifyDataSetChanged()
                    }catch (e: JSONException) { e.printStackTrace() }
                },
                Response.ErrorListener {
                    error: VolleyError ->
                    try {
                        Toast.makeText(null,
                                error.message + location.name,
                                Toast.LENGTH_LONG).show()

                       // startActivity(Intent(this, AddLocation::class.java))
                    }catch (e: JSONException) { e.printStackTrace() }
                }
        )
        volleyRequest!!.add(forecastRequest)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //add menu to main activity
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item != null) {
            when (item.itemId) {
                R.id.buAddLocation -> {
                    startActivity(Intent(this, AddLocation::class.java))
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

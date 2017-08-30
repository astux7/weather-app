package com.example.astux7.weatheralert.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
    private var favLocationList: ArrayList<WindForecast>? = null
    private var getFavLocationList: ArrayList<Location>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // DB data
        dbHandler = ForecastDatabaseHandler(this)
        // JSON data
        volleyRequest = Volley.newRequestQueue(this)

        favLocationList = ArrayList<WindForecast>()
        layoutManager = LinearLayoutManager(this)
        adapter = LocationListAdapter(favLocationList!!, this)
        // set up list
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        if(dbHandler!!.getLocationCount() > 0) {
            // load locations - and weather
            getFavLocationList = dbHandler!!.readLocations()
            for(item in getFavLocationList!!.iterator()) {
                val location = Location()
                location.id = item.id
                location.name = item.name

                getForecast(location, adapter!!)
                //TODO implement service fore wind
                // val forecast = WindForecast(location, "40mph", "350deg"   )
               // favLocationList!!.add(forecast)
            }
           // adapter!!.notifyDataSetChanged()
        } else {
            startActivity(Intent(this, AddLocation::class.java ))
        }
    }

    fun getForecast(location: Location, adapter: LocationListAdapter) {
        val url = FORECAST_API + location.name +  "&appid=" + FORECAST_KEY
        val forecastRequest = JsonObjectRequest(Request.Method.GET, url,
                Response.Listener {
                    response: JSONObject ->
                    try {
                        val result = response.getJSONObject("wind")
                        val speed = result["speed"].toString()
                        val direction = result["deg"].toString()
                        val forecast = WindForecast(location, speed, direction)
                        favLocationList!!.add(forecast)
                        adapter!!.notifyDataSetChanged()
                    }catch (e: JSONException) { e.printStackTrace() }
                },
                Response.ErrorListener {
                    error: VolleyError ->
                    try {
                        Log.d( "Error", error.toString())
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
                    var intent = Intent(this, AddLocation::class.java)
                    startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

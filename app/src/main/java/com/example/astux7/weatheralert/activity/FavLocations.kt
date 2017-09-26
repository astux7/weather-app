package com.example.astux7.weatheralert.activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.example.astux7.weatheralert.R
import com.example.astux7.weatheralert.data.ForecastNetworkClient
import com.example.astux7.weatheralert.data.LocationDatabaseHandler
import com.example.astux7.weatheralert.data.LocationListAdapter
import com.example.astux7.weatheralert.model.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback

class FavLocations : AppCompatActivity() {
    var dbHandler: LocationDatabaseHandler? = null
    val handler = Handler()
    private var adapter: LocationListAdapter? = null
    private var windForecastList: ArrayList<WindForecast>? = null
    private var favLocationList: ArrayList<Location>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.setTitle(R.string.fav_list_title);

        loadFavLocationForecast()
    }

    private fun loadFavLocationForecast() {
        // TODO replace depricate dialog
        val dialog= ProgressDialog(this)
        dialog.setMessage("Please wait...")
        dialog.show()
        dbHandler = LocationDatabaseHandler(this)
        if(dbHandler!!.getLocationCount() > 0) {
            // load locations - and weather
            favLocationList = dbHandler!!.readLocations()
            for(location in favLocationList!!.iterator()) {
                getForecast(location)
            }
            handler.postDelayed(Runnable { dialog.dismiss() }, 500)
        } else {
            startActivity(Intent(this, AddLocation::class.java ))
        }
    }

    private fun getForecast(location: Location) {
        val network = ForecastNetworkClient(applicationContext)
        val call = network.getForecastBy(location.city!!)

        windForecastList = ArrayList<WindForecast>()
        layoutManager = LinearLayoutManager(this)
        adapter = LocationListAdapter(windForecastList!!, this)
        // set up list
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        call.enqueue(object: Callback<WeatherForecast> {
            override fun onResponse(call: Call<WeatherForecast>?,
                                    response: retrofit2.Response<WeatherForecast>?) {
                if(response != null) {
                    val windForecast: WeatherForecast = response.body()!!
                    val wind = windForecast.wind

                    windForecastList!!.add(WindForecast(location, wind.speed, wind.deg))
                    adapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<WeatherForecast>?, t: Throwable?) {
                //Toast.makeText(null, "Problem getting forecast ..", Toast.LENGTH_LONG).show()
                t?.printStackTrace()
            }

        })
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
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

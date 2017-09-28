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
import android.widget.Toast
import com.example.astux7.weatheralert.R
import com.example.astux7.weatheralert.data.ForecastNetworkClient
import com.example.astux7.weatheralert.data.LocationDatabaseHandler
import com.example.astux7.weatheralert.data.LocationListAdapter
import com.example.astux7.weatheralert.model.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback

class FavLocations : AppCompatActivity() {

    private val dbHandler: LocationDatabaseHandler by lazy { LocationDatabaseHandler(applicationContext) }
    private val handler = Handler()
    private var adapter = LocationListAdapter(applicationContext)
    private var favLocationList = mutableListOf<Location>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.setTitle(R.string.fav_list_title);

        loadFavLocationForecast()
    }

    private fun loadFavLocationForecast() {
        // TODO replace deprecated dialog
        val dialog = ProgressDialog(this)
        dialog.setMessage("Please wait...")
        dialog.show()

        if (dbHandler.getLocationCount() > 0) {
            // load locations - and weather
            favLocationList = dbHandler.readLocations()
            favLocationList.forEach { location ->
                getForecast(location)
            }

            handler.postDelayed({ dialog.dismiss() }, 500)
        } else {
            startActivity(Intent(this, AddLocation::class.java))
        }
    }

    private fun getForecast(location: Location) {
        val network = ForecastNetworkClient(applicationContext)
        val call = network.getForecastBy(location.city!!)


        // set up list
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        call.enqueue(object : Callback<WeatherForecast> {
            override fun onResponse(call: Call<WeatherForecast>,
                                    response: retrofit2.Response<WeatherForecast>) {
                response.body()?.let { windForecast ->
                    val wind = windForecast.wind

                    adapter.addForecast(WindForecast(location, wind.speed, wind.deg))
                }

            }

            override fun onFailure(call: Call<WeatherForecast>, t: Throwable) {
                Toast.makeText(applicationContext, "Problem getting forecast for city ",
                        Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext, AddLocation::class.java)
                startActivity(intent)
//                 t?.printStackTrace()
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //add menu to main activity
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
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

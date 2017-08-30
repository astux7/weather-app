package com.example.astux7.weatheralert.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.example.astux7.weatheralert.R
import com.example.astux7.weatheralert.data.ForecastDatabaseHandler
import com.example.astux7.weatheralert.data.LocationListAdapter
import com.example.astux7.weatheralert.model.Location
import com.example.astux7.weatheralert.model.WindForecast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var dbHandler: ForecastDatabaseHandler? = null
    private var adapter: LocationListAdapter? = null
    private var favLocationList: ArrayList<WindForecast>? = null
    private var getFavLocationList: ArrayList<Location>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = ForecastDatabaseHandler(this)
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
                val forecast = WindForecast(location, "40mph", "350deg"   )

                //TODO implement service fore wind
                favLocationList!!.add(forecast)
            }
            adapter!!.notifyDataSetChanged()
        } else {
            startActivity(Intent(this, AddLocation::class.java ))
        }
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

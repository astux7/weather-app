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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var adapter: LocationListAdapter? = null
    private var favLocationList: ArrayList<Location>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    var dbHandler: ForecastDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        favLocationList = ArrayList<Location>()
        layoutManager = LinearLayoutManager(this)
        adapter = LocationListAdapter(favLocationList!!, this)

        // set up list
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        // load data
        // dbHandler = ForecastDatabaseHandler(this)
        //var favLocation: ArrayList<Location> = dbHandler!!.readLocation()
        val cityList = listOf<String>( "New York", "Leeds", "Vilnius", "Kaunas", "Sofia", "London")
        for (i in 0..5) {
            val loc = Location()
            loc.name = cityList[i]
            favLocationList!!.add(loc)
        }
        adapter!!.notifyDataSetChanged()

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

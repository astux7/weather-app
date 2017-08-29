package com.example.astux7.weatheralert.activity

import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.astux7.weatheralert.R
import com.example.astux7.weatheralert.data.ForecastDatabaseHandler
import kotlinx.android.synthetic.main.activity_add_location.*

class AddLocation : AppCompatActivity() {
    var dbHandler: ForecastDatabaseHandler? = null
    var location: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)

        dbHandler = ForecastDatabaseHandler(this)

        addLocation.setOnClickListener {
            if(!TextUtils.isEmpty(etLocation.text.toString())){
                location = Location(etLocation.text.toString())
                saveToDb(location!!)
            }
            else {
                Toast.makeText(this,"Please enter location", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun saveToDb(location: Location){
      //  dbHandler!!.createLocation(location)
    }


}

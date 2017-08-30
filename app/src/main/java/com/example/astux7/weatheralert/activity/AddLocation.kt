package com.example.astux7.weatheralert.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.astux7.weatheralert.R
import com.example.astux7.weatheralert.data.ForecastDatabaseHandler
import com.example.astux7.weatheralert.model.Location
import kotlinx.android.synthetic.main.activity_add_location.*

class AddLocation : AppCompatActivity() {
    var dbHandler: ForecastDatabaseHandler? = null
    var location: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)

        dbHandler = ForecastDatabaseHandler(this)

        saveButton.setOnClickListener {
            if(!TextUtils.isEmpty(etLocation.text.toString())){
                location = Location()
                location!!.name = etLocation.text.toString()
                saveToDb(location!!)
                Toast.makeText(this,"Saved", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else {
                Toast.makeText(this,"Please enter location", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun saveToDb(location: Location){
        dbHandler!!.createLocation(location)
    }


}

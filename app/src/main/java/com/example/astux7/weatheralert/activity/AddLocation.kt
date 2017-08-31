package com.example.astux7.weatheralert.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.astux7.weatheralert.R
import kotlinx.android.synthetic.main.activity_add_location.*

class AddLocation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)

        getForecastButton.setOnClickListener {
            val city = etLocation.text.toString()
            if(!TextUtils.isEmpty(city)){
                val intent = Intent(this, CurrentForecast::class.java)
                intent.putExtra("Location", city)
                startActivity(intent)
                finish()
            }
            else {
                Toast.makeText(this,"Please enter location", Toast.LENGTH_LONG).show()
            }
        }
    }
}

package com.example.astux7.weatheralert.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.astux7.weatheralert.R
import com.example.astux7.weatheralert.data.ForecastNetworkClient
import com.example.astux7.weatheralert.data.LocationDatabaseHandler
import com.example.astux7.weatheralert.model.*
import kotlinx.android.synthetic.main.activity_current_forecast.*
import retrofit2.Call
import retrofit2.Callback


class CurrentForecast : AppCompatActivity() {
    var dbHandler: LocationDatabaseHandler? = null
    var location: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_forecast)

        val city: String = intent.extras!!.get("Location").toString()
        getForecast(city)

        buttonAddToFav.setOnClickListener {
            dbHandler = LocationDatabaseHandler(this)
            saveToDb(city)
            Toast.makeText(this, city + " saved", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, FavLocations::class.java))
            finish()
        }

        backButton.setOnClickListener {
            startActivity(Intent(this, FavLocations::class.java))
            finish()
        }
    }

    private fun saveToDb(city: String) {
        location = Location()
        location?.city = city
        dbHandler!!.createLocation(location!!)
    }

    private fun getForecast(city: String) {
        val network = ForecastNetworkClient(applicationContext)
        val call = network.getForecastBy(city)

        call.enqueue(object: Callback<WeatherForecast> {
            override fun onResponse(call: Call<WeatherForecast>?,
                                    response: retrofit2.Response<WeatherForecast>?) {
                if(response != null) {
                    val windForecast: WeatherForecast = response.body()!!
                    val wind = windForecast.wind
                    presentData(wind, city)
                }
            }

            override fun onFailure(call: Call<WeatherForecast>?, t: Throwable?) {
                Toast.makeText(null, "Problem getting forecast for " + city,
                        Toast.LENGTH_LONG).show()
                t?.printStackTrace()
            }

        })
    }

    private fun presentData(wind: Wind, city: String){
        tvLocationName.text = city
        with(wind) {
            tvSpeed.text = speed.toString()
            tvDirection.text = deg.toString()
        }
    }
}



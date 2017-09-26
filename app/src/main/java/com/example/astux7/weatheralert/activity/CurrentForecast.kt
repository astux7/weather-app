package com.example.astux7.weatheralert.activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_forecast)

        val city: String = intent.extras!!.get("Location").toString()


        getForecastFor(city)

        buttonAddToFav.setOnClickListener {
            addToFavourite(city)
        }

        backButton.setOnClickListener {
            goToFavLocation()
        }
    }

    private fun addToFavourite(city: String) {
        saveToDb(city)
        Toast.makeText(this, "$city saved", Toast.LENGTH_LONG).show()
        goToFavLocation()
    }

    private fun goToFavLocation() {
        startActivity(Intent(this, FavLocations::class.java))
        finish()
    }

    private fun saveToDb(city: String) {
        dbHandler = LocationDatabaseHandler(this)
        dbHandler!!.createLocation(city)
    }

    private fun getForecastFor(city: String) {
        val network = ForecastNetworkClient(applicationContext)
        val call = network.getForecastBy(city)
        // TODO replace depricate dialog
        val dialog= ProgressDialog(this)
        dialog.setMessage("Please wait")
        dialog.show()

        call.enqueue(object: Callback<WeatherForecast> {
            override fun onResponse(call: Call<WeatherForecast>?,
                                    response: retrofit2.Response<WeatherForecast>?) {
                if(response != null) {
                    try {
                        val windForecast: WeatherForecast = response.body()!!
                        val wind = windForecast.wind
                        presentData(wind, city)
                        handler.postDelayed(Runnable { dialog.dismiss() }, 500)
                    } catch(e: Exception){
                        Toast.makeText(null, "Problem getting forecast for " + city,
                                Toast.LENGTH_LONG).show()
//                        val intent = Intent(applicationContext, AddLocation::class.java)
//                        context?.startActivity(intent)
                    }
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
            tvSpeed.text = "${speed.toString()} m/s"
            tvDirection.text = "${deg.toString()} $DEGREE"
        }
    }
}



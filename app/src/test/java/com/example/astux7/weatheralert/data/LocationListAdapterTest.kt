package com.example.astux7.weatheralert.data

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.astux7.weatheralert.data.LocationListAdapter.ViewHolder
import com.example.astux7.weatheralert.model.Location
import com.example.astux7.weatheralert.model.WindForecast
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import java.util.ArrayList

/**
 * Created by astux7 on 28/09/2017.
 */
class LocationListAdapterTest {

    private lateinit var adapter: LocationListAdapter

    @Mock
    private lateinit var mockView: View
    @Mock
    private lateinit var mockContext: Context
    @Mock
    private lateinit var mockTextView: TextView
    @Mock
    private lateinit var mockButton: Button
    var windForecastList = ArrayList<WindForecast>()
    var content = makeList(windForecastList)


    lateinit var holder: ViewHolder

    @Before
    fun setUp() {
        initMocks(this)
        adapter = LocationListAdapter(content, mockContext)
//        holder = adapter.ViewHolder(mockView, mockContext, windForecastList)
    }

    @Test
    fun onBindViewHolder() {
    }

    @Test
    fun test_getItemCount() {
        assertEquals(1, adapter.itemCount)
    }

    @Test
    fun onCreateViewHolder() {
    }

    fun makeLocation(city: String) :Location {
        var location = Location()
        location.city = city
        return location
    }

    fun makeList(windForecastList :ArrayList<WindForecast>): ArrayList<WindForecast> {
        val forecast = WindForecast(makeLocation("London"), 40.0f,90)
        windForecastList.add(forecast)
        return windForecastList
    }
}
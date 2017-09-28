package com.example.astux7.weatheralert.data

import android.content.Context
import android.support.constraint.R.id.parent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.example.astux7.weatheralert.R
import com.example.astux7.weatheralert.data.LocationListAdapter.ViewHolder
import com.example.astux7.weatheralert.model.Location
import com.example.astux7.weatheralert.model.WindForecast
import junit.framework.Assert.assertEquals
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never
import org.mockito.MockitoAnnotations.initMocks
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import java.util.ArrayList
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.mockito.Mockito.`when`
import java.util.zip.Inflater


/**
 * Created by astux7 on 28/09/2017.
 */
class LocationListAdapterTest {

    private lateinit var adapter: TestableLocationListAdapter

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

    @Mock
    lateinit var mockParent: ViewGroup

    @Mock
    lateinit var mockInflator: LayoutInflater

    lateinit var holder: ViewHolder

    @Before
    fun setUp() {

        initMocks(this)

        `when`(mockParent.context).thenReturn(mockContext)
       // `when`(mockInflator.inflate(anyInt(), eq(mockParent), eq(false))).thenReturn(mockView)
        `when`(mockView.findViewById<TextView>(R.id.tvLocationItem)).thenReturn(mockTextView)
        `when`(mockView.findViewById<TextView>(R.id.tvTodaySpeed)).thenReturn(mockTextView)
        `when`(mockView.findViewById<TextView>(R.id.tvTodayDirection)).thenReturn(mockTextView)
        `when`(mockView.findViewById<Button>(R.id.deleteButton)).thenReturn(mockButton)

        adapter = TestableLocationListAdapter(content, mockContext)
        holder = adapter.ViewHolder(mockView, mockContext, windForecastList)
    }

//    @Test
//    fun onBindViewHolder() {
//    }

    @Test
    fun test_getItemCount() {
        assertEquals(1, adapter.itemCount)
    }

    @Test
    fun test_on_create_view_holder() {
//        adapter.setMockView(mockView)
//        adapter.setMockInflater(mockInflator)
//        val viewHolder = adapter.onCreateViewHolder(mockParent, 0)
//        assertEquals(mockView, viewHolder.itemView)
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

    class TestableLocationListAdapter(private val list: ArrayList<WindForecast>,
                                      private val context: Context) : LocationListAdapter(list, context) {


        private var _mockView: View = View(context)
       // private lateinit var _mockInf: LayoutInflater(mockContext)

        fun setMockView(mockView: View) {
            this._mockView = mockView
        }


    }
}

package com.example.astux7.weatheralert.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.astux7.weatheralert.R
import com.example.astux7.weatheralert.activity.AddLocation
import com.example.astux7.weatheralert.model.DEGREE
import com.example.astux7.weatheralert.model.WindForecast
import kotlinx.android.synthetic.main.location.view.*
import java.util.*

/**
 * Created by astux7 on 29/08/2017.
 */

open class LocationListAdapter(private val context: Context) : RecyclerView.Adapter<LocationListAdapter.ViewHolder>() {

    val list = mutableListOf<WindForecast>()

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItem(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // Create actual data view - ticket
    override fun onCreateViewHolder(parent: ViewGroup?, position: Int): ViewHolder {
        // create our view from xml file
        val view = LayoutInflater.from(context).inflate(R.layout.location, parent, false)
        return ViewHolder(view, context, list)
    }

    // get the actual item when it is created from onCreateView
    inner class ViewHolder(itemView: View, context: Context, list: MutableList<WindForecast>) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val itemContext = context
        private val itemList = list
        private val favLocation = itemView.tvLocationItem
        private val todayWindSpeed = itemView.tvTodaySpeed
        private val todayWindDir = itemView.tvTodayDirection
        private val deleteButton = itemView.deleteButton

        fun bindItem(forecast: WindForecast) {
            favLocation.text = forecast.location.city
            todayWindSpeed.text = "${forecast.speed} m/s"
            todayWindDir.text = "${forecast.direction} $DEGREE"

            deleteButton.setOnClickListener(this) //register button
        }

        override fun onClick(view: View) {
            val itemPosition: Int = adapterPosition
            val favLocation = itemList[itemPosition].location

            when (view.id) {
                deleteButton.id -> {
                    favLocation.id?.let {
                        deleteLocation(it);
                    }
                    itemList.removeAt(adapterPosition)
                    if (list.size > 0) {
                        Toast.makeText(itemContext, "${favLocation.city} deleted", Toast.LENGTH_LONG).show()
                        notifyItemRemoved(adapterPosition)
                    } else {
                        context.startActivity(Intent(context, AddLocation::class.java))
                    }
                }
            }
        }

        private fun deleteLocation(id: Int) {
            val db = LocationDatabaseHandler(itemContext)
            db.deleteLocation(id)
        }
    }

    fun addForecast(windForecast: WindForecast) {
        list.add(windForecast)
        notifyDataSetChanged()
    }
}
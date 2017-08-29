package com.example.astux7.weatheralert.data

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.example.astux7.weatheralert.R
import com.example.astux7.weatheralert.model.Location

/**
 * Created by astux7 on 29/08/2017.
 */

class LocationListAdapter(private val list: ArrayList<Location>,
                          private val context: Context): RecyclerView.Adapter<LocationListAdapter.ViewHolder>() {
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
        return ViewHolder(view)
    }

    // get the actual item when it is created from onCreateView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(location: Location){
            var favLocation: TextView = itemView.findViewById<TextView>(R.id.tvLocationItem) as TextView
            var todayWindSpeed: TextView = itemView.findViewById<TextView>(R.id.tvTodaySpeed) as TextView
            var todayWindDir: TextView = itemView.findViewById<TextView>(R.id.tvTodayDirection) as TextView

            favLocation.text = location.name
            todayWindSpeed.text = "10 mph"
            todayWindDir.text = "SW"
        }
    }
}
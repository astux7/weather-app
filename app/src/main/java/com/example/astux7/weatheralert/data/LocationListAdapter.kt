package com.example.astux7.weatheralert.data

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.astux7.weatheralert.R
import com.example.astux7.weatheralert.activity.AddLocation
import com.example.astux7.weatheralert.model.Location
import com.example.astux7.weatheralert.model.WindForecast

/**
 * Created by astux7 on 29/08/2017.
 */

class LocationListAdapter(private val list: ArrayList<WindForecast>,
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
        return ViewHolder(view, context, list)
    }

    // get the actual item when it is created from onCreateView
    inner class ViewHolder(itemView: View, context: Context, list: ArrayList<WindForecast>) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var itemContext = context
        var itemList = list
        var location: Location? = null
        var favLocation = itemView.findViewById<TextView>(R.id.tvLocationItem) as TextView
        var todayWindSpeed = itemView.findViewById<TextView>(R.id.tvTodaySpeed) as TextView
        var todayWindDir = itemView.findViewById<TextView>(R.id.tvTodayDirection) as TextView
        var deleteButton = itemView.findViewById<Button>(R.id.deleteButton) as Button

        fun bindItem(forecast: WindForecast){
            location = forecast.location
            favLocation.text = location!!.name
            todayWindSpeed.text = forecast.speed
            todayWindDir.text = forecast.direction
            //register button
            deleteButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            var itemPosition: Int = adapterPosition
            var favLocation = itemList[itemPosition].location

            when(v!!.id) {
                deleteButton.id -> {
                    deleteLocation(favLocation!!.id!!)
                    itemList.removeAt(adapterPosition)
                    if(list.size > 0) {
                        Toast.makeText(itemContext, favLocation!!.name + " deleted", Toast.LENGTH_LONG).show()
                        notifyItemRemoved(adapterPosition)
                    }else { // do not show empty list
                        context.startActivity(Intent(context, AddLocation::class.java))
                    }
                }
            }
        }

        protected fun deleteLocation(id: Int) {
            var db: ForecastDatabaseHandler = ForecastDatabaseHandler(itemContext)
            db.deleteLocation(id)
        }
    }
}
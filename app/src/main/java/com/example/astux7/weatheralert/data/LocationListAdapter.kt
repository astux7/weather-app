package com.example.astux7.weatheralert.data

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
        return ViewHolder(view, context, list)
    }

    // get the actual item when it is created from onCreateView
    inner class ViewHolder(itemView: View, context: Context, list: ArrayList<Location>) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var itemContext = context
        var itemList = list
        var favLocation = itemView.findViewById<TextView>(R.id.tvLocationItem) as TextView
        var todayWindSpeed = itemView.findViewById<TextView>(R.id.tvTodaySpeed) as TextView
        var todayWindDir = itemView.findViewById<TextView>(R.id.tvTodayDirection) as TextView
        var deleteButton = itemView.findViewById<Button>(R.id.deleteButton) as Button

        fun bindItem(location: Location){
            favLocation.text = location.name
            todayWindSpeed.text = "10 mph"
            todayWindDir.text = "SW"
            //register button
            deleteButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            var itemPosition: Int = adapterPosition
            var favLocation = itemList[itemPosition]

            when(v!!.id) {
                deleteButton.id -> {
                    deleteLocation(favLocation.id!!)
                    itemList.removeAt(adapterPosition)
                    Toast.makeText(itemContext, favLocation.name + " deleted", Toast.LENGTH_LONG).show()
                    notifyItemRemoved(adapterPosition)
                }
            }
        }

        fun deleteLocation(id: Int) {
            var db: ForecastDatabaseHandler = ForecastDatabaseHandler(itemContext)
            db.deleteLocation(id)
        }
    }
}
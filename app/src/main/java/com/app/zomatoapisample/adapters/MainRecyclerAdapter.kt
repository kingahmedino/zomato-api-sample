package com.app.zomatoapisample.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.zomatoapisample.R
import com.app.zomatoapisample.models.Restaurant

class MainRecyclerAdapter(
    private val mRestaurants: MutableList<Restaurant>,
    private val mContext: Context
) : RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.main_rv_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = mRestaurants.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = mRestaurants[position]
        holder.restaurantName?.text = restaurant.name
        holder.restaurantLocality?.text = restaurant.locality
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaurantName = itemView.findViewById<TextView?>(R.id.name)
        val restaurantLocality = itemView.findViewById<TextView?>(R.id.locality)
    }
}
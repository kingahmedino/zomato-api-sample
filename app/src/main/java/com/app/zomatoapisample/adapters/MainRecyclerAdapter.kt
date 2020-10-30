package com.app.zomatoapisample.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.zomatoapisample.databinding.MainRvListItemBinding
import com.app.zomatoapisample.models.Restaurant

class MainRecyclerAdapter(
    private val mRestaurants: MutableList<Restaurant>,
    private val mContext: Context
) : RecyclerView.Adapter<MainRecyclerAdapter.BindingHolder>() {

    private val layoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val binding = MainRvListItemBinding.inflate(layoutInflater, parent, false)
        return BindingHolder(binding.root)
    }

    override fun getItemCount() = mRestaurants.size

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val restaurant = mRestaurants[position]
        holder.mBinding.restaurant = restaurant
        holder.mBinding.executePendingBindings()
    }

    inner class BindingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding: MainRvListItemBinding = DataBindingUtil.bind(itemView)!!
    }
}
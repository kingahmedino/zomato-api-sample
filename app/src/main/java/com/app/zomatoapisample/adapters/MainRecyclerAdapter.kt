package com.app.zomatoapisample.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.zomatoapisample.R
import com.app.zomatoapisample.databinding.MainRvListItemBinding
import com.app.zomatoapisample.models.Restaurant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

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
        Glide.with(mContext).load(restaurant.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(mContext.resources.getDrawable(R.drawable.ic_error))
            .into(holder.mBinding.imageView)
        holder.mBinding.executePendingBindings()
    }

    inner class BindingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding: MainRvListItemBinding = DataBindingUtil.bind(itemView)!!
    }
}
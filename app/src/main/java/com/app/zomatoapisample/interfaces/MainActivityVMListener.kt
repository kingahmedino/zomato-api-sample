package com.app.zomatoapisample.interfaces

import androidx.lifecycle.MutableLiveData
import com.app.zomatoapisample.models.Restaurant

interface MainActivityVMListener {
    fun onRestaurantResponseSuccessful(mutableLiveData: MutableLiveData<MutableList<Restaurant>>)
    fun onStarted()
    fun onSuccess(message: String)
    fun onFailure()
}
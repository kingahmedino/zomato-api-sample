package com.app.zomatoapisample.interfaces

import androidx.lifecycle.MutableLiveData
import com.app.zomatoapisample.models.Restaurant

interface DataRepoListener {
    fun onSuccess(mutableLiveData: MutableLiveData<MutableList<Restaurant>>)
    fun onFailure(message: String)
}
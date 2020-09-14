package com.app.zomatoapisample.interfaces

interface GetLocationListener {
    fun onStarted()
    fun onSuccess(message: String)
    fun onFailure()
}
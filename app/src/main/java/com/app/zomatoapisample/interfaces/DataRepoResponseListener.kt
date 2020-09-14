package com.app.zomatoapisample.interfaces

interface DataRepoResponseListener {
    fun onSuccess(s: String)
    fun onFailure(message: String)
}
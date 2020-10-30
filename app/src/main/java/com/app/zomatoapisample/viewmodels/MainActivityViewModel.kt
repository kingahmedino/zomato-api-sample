package com.app.zomatoapisample.viewmodels

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.zomatoapisample.interfaces.DataRepoListener
import com.app.zomatoapisample.interfaces.MainActivityVMListener
import com.app.zomatoapisample.models.LocationInfo
import com.app.zomatoapisample.models.Restaurant
import com.app.zomatoapisample.repositories.DataRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivityViewModel: ViewModel(), DataRepoListener {
    var mMainActivityVMListener: MainActivityVMListener? = null
    private val requestPermissionCode = 1
    var mLocation: Location? = null
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    fun setGetLocationListener(mainActivityVMListener: MainActivityVMListener){
        mMainActivityVMListener = mainActivityVMListener
    }

    fun searchRestaurants(query: String){
        DataRepository.getRestaurants(query)
    }

    fun getUserLocation(){
        mMainActivityVMListener?.onStarted()
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mMainActivityVMListener as Activity)
        getLastLocation()
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                mMainActivityVMListener as Activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission()
        } else {
            mFusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    mLocation = location
                    if (location != null) {
                        LocationInfo.latitude = location.latitude
                        LocationInfo.longitude = location.longitude
                        mMainActivityVMListener?.onSuccess("Success")
                        DataRepository.setDataRepoResponseListener(this)
                        DataRepository.getRestaurants(DEFAULT_QUERY)
                    }
                }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            mMainActivityVMListener as Activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            requestPermissionCode
        )
        getLastLocation()
    }

    override fun onSuccess(mutableLiveData: MutableLiveData<MutableList<Restaurant>>) {
        mMainActivityVMListener?.onSuccess("Got restaurants successfully")
        mMainActivityVMListener?.onRestaurantResponseSuccessful(mutableLiveData)
    }

    override fun onFailure(message: String) {
        mMainActivityVMListener?.onFailure()
    }

    companion object{
        private const val DEFAULT_QUERY = ""
    }
}
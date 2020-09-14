package com.app.zomatoapisample.viewmodels

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.app.zomatoapisample.models.LocationInfo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivityViewModel(context: Context): ViewModel() {
    val requestPermissionCode = 1
    var mLocation: Location? = null
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private val mContext = context

    fun getUserLocation(){
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext)
        getLastLocation()
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                mContext,
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
                    }
                }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            mContext as Activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            requestPermissionCode
        )
    }
}
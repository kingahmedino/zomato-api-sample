package com.app.zomatoapisample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.zomatoapisample.interfaces.MainActivityVMListener
import com.app.zomatoapisample.models.LocationInfo
import com.app.zomatoapisample.models.Restaurant
import com.app.zomatoapisample.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityVMListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.setGetLocationListener(this)
        mainActivityViewModel.getUserLocation()
    }

    override fun onRestaurantResponseSuccessful(mutableLiveData: MutableLiveData<MutableList<Restaurant>>) {
        mutableLiveData.observe(this, Observer {
            Toast.makeText(this, it.size, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onStarted() {
        mainActivityPB.visibility = View.VISIBLE
        textView.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        Toast.makeText(this, "$message + ${LocationInfo.latitude}", Toast.LENGTH_SHORT).show()

        mainActivityPB.visibility = View.INVISIBLE
        textView.visibility = View.INVISIBLE
    }

    override fun onFailure() {

    }
}
package com.app.zomatoapisample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.zomatoapisample.adapters.MainRecyclerAdapter
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
        mutableLiveData.observe(this, Observer { restaurants ->
            mainActivityRV.also {
                it.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                it.setHasFixedSize(true)
                it.adapter = MainRecyclerAdapter(restaurants, this)
            }
        })
    }

    override fun onStarted() {
        mainActivityRV.visibility = View.INVISIBLE
        mainActivityPB.visibility = View.VISIBLE
        textView.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        Toast.makeText(this, "$message + ${LocationInfo.latitude}", Toast.LENGTH_SHORT).show()

        mainActivityPB.visibility = View.INVISIBLE
        textView.visibility = View.INVISIBLE
        mainActivityRV.visibility = View.VISIBLE
    }

    override fun onFailure() {

    }
}
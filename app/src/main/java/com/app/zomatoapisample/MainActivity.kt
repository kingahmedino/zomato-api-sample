package com.app.zomatoapisample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.zomatoapisample.interfaces.GetLocationListener
import com.app.zomatoapisample.models.LocationInfo
import com.app.zomatoapisample.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), GetLocationListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.setGetLocationListener(this)
        mainActivityViewModel.getUserLocation()
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
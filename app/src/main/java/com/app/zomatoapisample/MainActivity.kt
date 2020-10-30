package com.app.zomatoapisample

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.zomatoapisample.adapters.MainRecyclerAdapter
import com.app.zomatoapisample.databinding.ActivityMainBinding
import com.app.zomatoapisample.interfaces.MainActivityVMListener
import com.app.zomatoapisample.models.LocationInfo
import com.app.zomatoapisample.models.Restaurant
import com.app.zomatoapisample.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityVMListener {
    var isLoading = false
    lateinit var mainBinding: ActivityMainBinding
    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainBinding.isLoading = isLoading

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.setGetLocationListener(this)
        mainActivityViewModel.getUserLocation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    mainBinding.mainActivityRV.scrollToPosition(0)
                    mainActivityViewModel.searchRestaurants(query)
                    mainBinding.isLoading = true
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        return true
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
        mainBinding.isLoading = false
    }

    override fun onStarted() {
        mainBinding.isLoading = true
    }

    override fun onSuccess(message: String) {
        Toast.makeText(
            this, "$message\nLatitude: ${LocationInfo.latitude} Longitude: ${LocationInfo.longitude}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onFailure() {
        mainBinding.isLoading = false
        Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG).show()
    }
}
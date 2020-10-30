package com.app.zomatoapisample.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.zomatoapisample.backend.BackEnd
import com.app.zomatoapisample.interfaces.DataRepoListener
import com.app.zomatoapisample.models.LocationInfo
import com.app.zomatoapisample.models.Restaurant
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DataRepository {
    private const val TAG = "DataRepository"
    private var mDataRepoListener: DataRepoListener? = null

    fun setDataRepoResponseListener(dataRepoListener: DataRepoListener){
        mDataRepoListener = dataRepoListener
    }

    fun getRestaurants(query: String){
        val mutableLiveResponse = MutableLiveData<MutableList<Restaurant>>()

        BackEnd().getRestaurants("1b3c8b37ea96785391fa55c288ac385c",
            LocationInfo.latitude, LocationInfo.longitude, query,50)
            .enqueue(object: Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d(TAG, "getRestaurant: onFailure")
                    mDataRepoListener?.onFailure(t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful){
                        mutableLiveResponse.value = getRestaurantsFromJSON(response.body()?.string())
                        mDataRepoListener?.onSuccess(mutableLiveResponse)
                    }
                }

            })
    }

    fun getRestaurantsFromJSON(string: String?): MutableList<Restaurant>? {
        val outputList = mutableListOf<Restaurant>()
        Log.d("DataRepo", "getRestaurantsFromJSON: $string")
        val parent = JSONObject(string!!)
        val restaurantArray = parent.getJSONArray("restaurants")
        for (i in 0 until restaurantArray.length()){
            val json = restaurantArray[i] as JSONObject
            val jsonObject = json.getJSONObject("restaurant")
            val id = jsonObject.getString("id")
            val name = jsonObject.getString("name")
            val featuredImage = jsonObject.getString("featured_image")
            val locationObject = jsonObject.getJSONObject("location")
            val address = locationObject.getString("address")
            val locality = locationObject.getString("locality")
            val city = locationObject.getString("city")
            val latitude = locationObject.getDouble("latitude")
            val longitude = locationObject.getDouble("longitude")
            Log.d(TAG, "getRestaurantsFromJSON: $featuredImage")

            outputList.add(Restaurant(id, name, address, city, featuredImage, longitude, latitude, locality))
        }
        return outputList
    }
}
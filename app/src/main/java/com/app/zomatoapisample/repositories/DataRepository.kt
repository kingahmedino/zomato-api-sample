package com.app.zomatoapisample.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.zomatoapisample.backend.BackEnd
import com.app.zomatoapisample.interfaces.DataRepoListener
import com.app.zomatoapisample.models.Restaurant
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DataRepository {
    private var mDataRepoListener: DataRepoListener? = null

    fun setDataRepoResponseListener(dataRepoListener: DataRepoListener){
        mDataRepoListener = dataRepoListener
    }

    fun getRestaurants(){
        val mutableLiveResponse = MutableLiveData<MutableList<Restaurant>>()

        BackEnd().getRestaurants("1b3c8b37ea96785391fa55c288ac385c",
            -15.794896, -47.928253, 2)
            .enqueue(object: Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("DataRepo", "getRestaurant: onFailure")
                    mDataRepoListener?.onFailure(t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful){
                        Log.d("DataRepo", "getRestaurant: onSuccessful")
                        mutableLiveResponse.value = getRestaurantsFromJSON(response.body().toString())
                        mDataRepoListener?.onSuccess(mutableLiveResponse)
                    }
                }

            })
    }

    fun getRestaurantsFromJSON(string: String): MutableList<Restaurant>? {
        val outputList = mutableListOf<Restaurant>()
        val parent = JSONObject(string)
        val restaurantArray = parent.getJSONArray("restaurant")
        for (i in 0..restaurantArray.length()){
            val jsonObject = restaurantArray[i] as JSONObject
            val id = jsonObject.getString("id")
            val name = jsonObject.getString("name")
            val locationObject = jsonObject.getJSONObject("location")
            val address = locationObject.getString("address")
            val locality = locationObject.getString("locality")
            val city = locationObject.getString("city")
            val latitude = locationObject.getDouble("latitude")
            val longitude = locationObject.getDouble("longitude")

            outputList.add(Restaurant(id, name, address, city, longitude, latitude, locality))
        }
        return outputList
    }
}
package com.app.zomatoapisample.repositories

import com.app.zomatoapisample.backend.BackEnd
import com.app.zomatoapisample.interfaces.DataRepoResponseListener
import com.app.zomatoapisample.models.Restaurant
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DataRepository {
    private var mDataRepoResponseListener: DataRepoResponseListener? = null
    var restaurantList = mutableListOf<Restaurant>()

    fun setDataRepoResponseListener(dataRepoResponseListener: DataRepoResponseListener){
        mDataRepoResponseListener = dataRepoResponseListener
    }

    fun getRestaurants(){
        BackEnd().getRestaurants("1b3c8b37ea96785391fa55c288ac385c",
            -15.794896, -47.928253, 2)
            .enqueue(object: Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    mDataRepoResponseListener?.onFailure(t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful){
                        mDataRepoResponseListener?.onSuccess("Successful")
                    }
                }

            })
    }
}
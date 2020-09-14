package com.app.zomatoapisample.backend

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BackEnd {
    @GET("restaurant/search")
    fun getRestaurants(
        @Header("user_key") userKey: String,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("count") count: Int
    ): Call<ResponseBody>

    companion object{
        operator fun invoke(): BackEnd{
            return Retrofit.Builder()
                .baseUrl("https://developers.zomato.com/documentation/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BackEnd::class.java)
        }
    }
}
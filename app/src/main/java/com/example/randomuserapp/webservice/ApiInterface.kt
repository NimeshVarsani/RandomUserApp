package com.example.randomuserapp.webservice


import com.example.randomuserapp.data.User
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("https://randomuser.me/api/")
    fun getUsers(@Query("api_key") key: String): Call<User>
}
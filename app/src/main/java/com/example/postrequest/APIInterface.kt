package com.example.postrequest

import UserItem
import Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {

    @POST("/test/")
    fun addUser(@Body data: UserItem): Call<UserItem?>?

    @GET("/test/")
    fun getUsers(): Call<Users?>?
}
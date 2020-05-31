package com.example.assignment.network

import com.example.assignment.models.DetailAPIResponse
import com.example.assignment.models.LoginRequest
import com.google.gson.JsonArray
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface RetrofitServices {

    @GET
    fun getJsonResponse(@Url url: String?): Call<JsonArray>

    @GET
    fun getDetailAPIResponse(@Url url: String?) : Deferred<Response<DetailAPIResponse?>>

     @POST
     fun postLoginDetails(@Body loginRequest : LoginRequest)

     companion object {
         operator fun invoke(): RetrofitServices{
             return Retrofit.Builder()
                 .baseUrl("https://hacker-news.firebaseio.com/v0/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .addCallAdapterFactory(CoroutineCallAdapterFactory())
                 .build()
                 .create(RetrofitServices::class.java)
         }
     }
}
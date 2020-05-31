package com.example.assignment.repositories

import com.example.assignment.models.DetailAPIResponse
import com.example.assignment.network.RetrofitServices
import kotlinx.coroutines.Deferred
import retrofit2.Response

class DashboardRepository {

    suspend fun getDetailItem(url: String): Response<DetailAPIResponse?> {
        val response =  RetrofitServices().getDetailAPIResponse(url).await()
        return response
    }

}
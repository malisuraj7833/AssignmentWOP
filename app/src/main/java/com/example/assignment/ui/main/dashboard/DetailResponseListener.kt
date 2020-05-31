package com.example.assignment.ui.main.dashboard

import com.example.assignment.models.DetailAPIResponse
import retrofit2.Response

interface DetailResponseListener {
    fun onSuccess(detailAPIResponse: DetailAPIResponse?)
    fun onFailure(message:String)
}
package com.example.assignment.ui.main.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.assignment.models.DetailAPIResponse
import com.example.assignment.repositories.DashboardRepository
import com.example.assignment.util.MyCoroutines
import retrofit2.Response

class DashboardViewModel : ViewModel() {

    private lateinit var mDetailAPIResponse: DetailResponseListener
    private var detailedApiResponseArrayList = ArrayList<DetailAPIResponse?>()


    fun setListener(detailResponseListener: DetailResponseListener)
    {
        mDetailAPIResponse = detailResponseListener
    }

    fun makeAPICall(url: String) {
        MyCoroutines.main {
            val response = DashboardRepository().getDetailItem(url)
            if (response.isSuccessful){
                //Log.d("####", response.body().toString())
                mDetailAPIResponse.onSuccess(response.body())
                detailedApiResponseArrayList.add(response.body())
            }else{
                Log.e("####", response.errorBody().toString())
            }
        }
    }

    fun getAPIResponseArray() : ArrayList<DetailAPIResponse?>
    {
        return detailedApiResponseArrayList
    }
}
package com.example.assignment.network

import android.content.Context
import com.example.assignment.models.DetailAPIResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private var mContext: Context? = null
private var retrofit: Retrofit? = null
private var httpClientBuilder: OkHttpClient.Builder? = null
private const val TIME_OUT = 30


class RetrofitBuilder {

    companion object {
        fun getInstance(context: Context?): Retrofit? {
            mContext = context
            if (retrofit == null) {

                val cacheSize = 10 * 1024 * 1024 // 10 MB
                val cache =
                    Cache(mContext!!.getCacheDir(), cacheSize.toLong())
                httpClientBuilder = OkHttpClient.Builder()
                    .readTimeout(
                        TIME_OUT.toLong(),
                        TimeUnit.SECONDS
                    )
                    .cache(cache)

                initHttpLogging()
                val builder: Retrofit.Builder = Retrofit.Builder()
                    .baseUrl(RetrofitConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(httpClientBuilder?.build())
                retrofit = builder.build()
            }
            return retrofit
        }

        private fun initHttpLogging() {
            var logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder?.addInterceptor(logging)
        }
    }

}
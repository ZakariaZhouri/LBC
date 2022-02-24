package com.example.lbc.data.api

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class Interceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder().maxAge(1, TimeUnit.DAYS).build()
        return if (response.isSuccessful)
            response
        else {
            response.newBuilder().header("Cache-Control", cacheControl.toString()).build()
        }
    }
}
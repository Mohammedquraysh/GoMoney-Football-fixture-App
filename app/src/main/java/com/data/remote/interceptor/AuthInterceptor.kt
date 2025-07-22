package com.data.remote.interceptor

import com.example.gomoneyfootballfixtureassessment.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-Auth-Token", BuildConfig.API_KEY)
            .build()
        return chain.proceed(request)
    }
}
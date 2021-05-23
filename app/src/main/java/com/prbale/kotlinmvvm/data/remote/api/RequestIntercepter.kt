package com.prbale.kotlinmvvm.data.remote.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * This okhttp interceptor is responsible for adding the common query
 * parameters and headers for every service calls
 */
class RequestInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val originalHttpUrl: HttpUrl = originalRequest.url()
        val url = originalHttpUrl
            .newBuilder()
            .addQueryParameter("api_key", ApiConstants.API_KEY)
            .build()

        val request = originalRequest
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}
package com.prbale.kotlinmvvm.base.api

import com.prbale.kotlinmvvm.features.museums.model.MuseumList
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object ApiClient {

    private var servicesApiInterface: ServicesApiInterface? = null

    fun build(): ServicesApiInterface {

        val builder = Retrofit.Builder()
            .baseUrl(AppConstants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())

        val httpClient = OkHttpClient
            .Builder()
            .addInterceptor(interceptor())

        val retrofit: Retrofit = builder
            .client(httpClient.build())
            .build()

        servicesApiInterface = retrofit.create(ServicesApiInterface::class.java)

        return servicesApiInterface as ServicesApiInterface
    }

    private fun interceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Service Interface
    interface ServicesApiInterface {

        @GET("/v3/c62f311e-1219-4839-929c-15d5e93f9e29/")
        fun museums(): Call<MuseumList>

        @GET("/v3/c62f311e-1219-4839-929c-15d5e93f9e29/")
        fun fetchMuseums(): Single<MuseumList>
    }
}
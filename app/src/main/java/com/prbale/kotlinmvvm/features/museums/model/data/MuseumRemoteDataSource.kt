package com.prbale.kotlinmvvm.features.museums.model.data

import com.prbale.kotlinmvvm.base.api.ApiClient
import com.prbale.kotlinmvvm.base.api.OperationCallback
import com.prbale.kotlinmvvm.features.museums.model.Museum
import com.prbale.kotlinmvvm.features.museums.model.MuseumDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MuseumRemoteDataSource(apiClient: ApiClient) : MuseumDataSource {

    private var call: Call<MuseumResponse>? = null
    private val service = apiClient.build()

    override fun retrieveMuseums(callback: OperationCallback<Museum>) {

        call = service.museums()

        call?.enqueue(object : Callback<MuseumResponse> {
            override fun onFailure(call: Call<MuseumResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<MuseumResponse>,
                response: Response<MuseumResponse>
            ) {
                response.body()?.let {
                    if (response.isSuccessful && (it.isSuccess())) {
                        callback.onSuccess(it.data)
                    } else {
                        callback.onError(it.msg)
                    }
                }
            }
        })
    }
}
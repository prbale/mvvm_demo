package com.prbale.kotlinmvvm.features.museums.model.data

import com.prbale.kotlinmvvm.base.api.ApiClient
import com.prbale.kotlinmvvm.base.api.OperationCallback
import com.prbale.kotlinmvvm.features.museums.model.Museum
import com.prbale.kotlinmvvm.features.museums.model.MuseumDataSource
import com.prbale.kotlinmvvm.features.museums.model.MuseumList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MuseumRemoteDataSource(apiClient: ApiClient) : MuseumDataSource {

    private var call: Call<MuseumList>? = null
    private val service = apiClient.build()

    override fun retrieveMuseums(callback: OperationCallback<MuseumList>) {

        call = service.museums()

        call?.enqueue(object : Callback<MuseumList> {
            override fun onFailure(call: Call<MuseumList>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<MuseumList>,
                response: Response<MuseumList>
            ) {
                response.body()?.let {
                    if (response.isSuccessful && (it.isSuccess())) {
                        callback.onSuccess(it)
                    } else {
                        callback.onError(it.msg)
                    }
                }
            }
        })
    }
}
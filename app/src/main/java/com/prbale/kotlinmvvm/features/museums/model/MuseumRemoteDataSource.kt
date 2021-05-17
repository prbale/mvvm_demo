package com.prbale.kotlinmvvm.features.museums.model

import com.prbale.kotlinmvvm.base.api.ApiClient
import com.prbale.kotlinmvvm.base.api.OperationCallback
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MuseumRemoteDataSource(apiClient: ApiClient) : MuseumDataSource {

    private var call: Call<MuseumList>? = null
    private val service = apiClient.build()

    override fun fetchMuseums(): Single<MuseumList> {
        return service.fetchMuseums()
    }

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
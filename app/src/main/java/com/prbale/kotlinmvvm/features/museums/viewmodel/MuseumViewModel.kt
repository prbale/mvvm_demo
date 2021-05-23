package com.prbale.kotlinmvvm.features.museums.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prbale.kotlinmvvm.data.remote.api.Resource
import com.prbale.kotlinmvvm.data.remote.api.OperationCallback
import com.prbale.kotlinmvvm.features.museums.model.MuseumList
import com.prbale.kotlinmvvm.features.museums.model.MuseumRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MuseumViewModel(private val repository: MuseumRepository) : ViewModel() {

    private val museumsLiveData = MutableLiveData<Resource<MuseumList>>()

    // Using Retrofit - enqueue call
    fun loadMuseums() {

        museumsLiveData.postValue(Resource.loading(null))

        repository.fetchMuseums(object : OperationCallback<MuseumList> {
            override fun onError(error: String?) {
                museumsLiveData.postValue(Resource.error(error, null))
            }

            override fun onSuccess(data: MuseumList?) {
                museumsLiveData.postValue(Resource.success(data))
            }
        })
    }

    // Using Retrofit - RxJava
    fun fetchMuseums() {

        museumsLiveData.postValue(Resource.loading(null))

        repository.fetchMuseums()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    museumsLiveData.postValue(Resource.success(it))
                },
                {
                    museumsLiveData.postValue(Resource.error(it.message, null))
                }
            )
    }

    fun getMuseums(): LiveData<Resource<MuseumList>> {
        return museumsLiveData
    }
}
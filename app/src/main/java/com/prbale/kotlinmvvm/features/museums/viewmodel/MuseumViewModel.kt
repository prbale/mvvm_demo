package com.prbale.kotlinmvvm.features.museums.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prbale.kotlinmvvm.base.Resource
import com.prbale.kotlinmvvm.base.api.OperationCallback
import com.prbale.kotlinmvvm.features.museums.model.Museum
import com.prbale.kotlinmvvm.features.museums.model.MuseumList
import com.prbale.kotlinmvvm.features.museums.model.MuseumRepository

class MuseumViewModel(private val repository: MuseumRepository) : ViewModel() {

    private val museumsLiveData = MutableLiveData<Resource<MuseumList>>()

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

    fun getMuseums(): LiveData<Resource<MuseumList>> {
        return museumsLiveData
    }
}
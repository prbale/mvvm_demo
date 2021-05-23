package com.prbale.kotlinmvvm.features.museums.model

import com.prbale.kotlinmvvm.data.remote.api.OperationCallback
import io.reactivex.rxjava3.core.Single

interface MuseumDataSource {
    fun retrieveMuseums(callback: OperationCallback<MuseumList>)
    fun fetchMuseums(): Single<MuseumList>
}
package com.prbale.kotlinmvvm.features.museums.model

import com.prbale.kotlinmvvm.base.api.OperationCallback
import com.prbale.kotlinmvvm.base.extensions.runDelayed
import io.reactivex.rxjava3.core.Single

class MuseumRepository(private val museumDataSource: MuseumDataSource) {

    fun fetchMuseums(callback: OperationCallback<MuseumList>) {
        runDelayed {
            museumDataSource.retrieveMuseums(callback)
        }
    }

    fun fetchMuseums(): Single<MuseumList> {
        return museumDataSource.fetchMuseums()
    }
}
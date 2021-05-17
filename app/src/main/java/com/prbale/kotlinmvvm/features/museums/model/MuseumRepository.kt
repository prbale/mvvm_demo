package com.prbale.kotlinmvvm.features.museums.model

import com.prbale.kotlinmvvm.base.api.OperationCallback
import com.prbale.kotlinmvvm.base.extensions.runDelayed

class MuseumRepository(private val museumDataSource: MuseumDataSource) {

    fun fetchMuseums(callback: OperationCallback<MuseumList>) {
        runDelayed {
            museumDataSource.retrieveMuseums(callback)
        }
    }
}
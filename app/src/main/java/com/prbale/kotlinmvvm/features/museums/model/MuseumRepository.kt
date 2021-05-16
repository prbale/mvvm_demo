package com.prbale.kotlinmvvm.features.museums.model

import android.os.Handler
import com.prbale.kotlinmvvm.base.api.OperationCallback

class MuseumRepository(private val museumDataSource: MuseumDataSource) {

    fun fetchMuseums(callback: OperationCallback<Museum>) {
        Handler().postDelayed({
            museumDataSource.retrieveMuseums(callback)
        }, 5000)
    }
}
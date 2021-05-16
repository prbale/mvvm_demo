package com.prbale.kotlinmvvm.features.museums.model

import com.prbale.kotlinmvvm.base.api.OperationCallback

interface MuseumDataSource {

    fun retrieveMuseums(callback: OperationCallback<Museum>)
}
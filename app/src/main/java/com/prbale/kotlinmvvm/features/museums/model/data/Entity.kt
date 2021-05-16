package com.prbale.kotlinmvvm.features.museums.model.data

import com.prbale.kotlinmvvm.features.museums.model.Museum

data class MuseumResponse(val status: Int?, val msg: String?, val data: List<Museum>?) {
    fun isSuccess(): Boolean = (status == 200)
}
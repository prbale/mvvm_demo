package com.prbale.kotlinmvvm.features.museums.model

import java.io.Serializable

data class MuseumList(
    val msg: String? = null,
    val status: Int? = null,
    val data: List<Museum>? = null
) {
    fun isSuccess(): Boolean = (status == 200)
}

data class Museum(val id: Int, val name: String, val photo: String) : Serializable

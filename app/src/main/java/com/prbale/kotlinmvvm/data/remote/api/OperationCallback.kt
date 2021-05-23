package com.prbale.kotlinmvvm.data.remote.api

interface OperationCallback<T> {
    fun onSuccess(data: T?)
    fun onError(error: String?)
}
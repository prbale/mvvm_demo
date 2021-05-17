package com.prbale.kotlinmvvm.base.api

interface OperationCallback<T> {
    fun onSuccess(data: T?)
    fun onError(error: String?)
}
package com.prbale.kotlinmvvm.data.remote.api

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 * Author: Prashant Bale
 */
class Resource<out T> private constructor(val status: Status, val data: T?, val message: String?) {

    // Status Enum Class
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {

        fun <T> success(data : T?): Resource<T> {
            return Resource(
                status = Status.SUCCESS,
                data = data,
                message = null
            )
        }

        fun <T> error(msg: String?, data : T?) : Resource<T> {
            return Resource(
                status = Status.ERROR,
                data = data,
                message = msg
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                status = Status.LOADING,
                data = data,
                message = null
            )
        }
    }
}
package com.example.smarthome.utilities

data class Resource<out T>(
        val status: Status,
        val data: T?,
        val message: String?
){
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        NONE
    }

    companion object {
        fun <T> success(data: T, message: String? = null): Resource<T> {
            return Resource(Status.SUCCESS, data, message)
        }

        fun success(message: String? = null): Resource<Nothing> {
            return Resource(Status.SUCCESS, null, message)
        }

        fun <T> error(message: String?, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun none(): Resource<Nothing> {
            return Resource(Status.NONE, null, null)
        }

    }
}

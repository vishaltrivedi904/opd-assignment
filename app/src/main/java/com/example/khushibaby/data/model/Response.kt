package com.example.khushibaby.data.model

sealed class Response<out T> {
    object Loading : Response<Nothing>()
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val message: Exception, val data: Any?) :
        Response<Nothing>()
}
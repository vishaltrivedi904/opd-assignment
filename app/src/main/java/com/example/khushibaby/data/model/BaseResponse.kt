package com.example.khushibaby.data.model

data class BaseResponse(
    val code: Int,
    val message: String,
    val status: String
){
    val isSuccess: Boolean
        get() = code in 200..299
}
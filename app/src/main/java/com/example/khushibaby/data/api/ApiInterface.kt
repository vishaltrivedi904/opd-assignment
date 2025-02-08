package com.example.animeapplication.data.api

import com.example.khushibaby.data.model.BaseResponse
import com.example.khushibaby.data.model.Visit
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {


    @POST("complete_visit")
    suspend fun markAsComplete(@Body visit: HashMap<String,Any>): Response<BaseResponse>

    @POST("syncVisits")
    suspend fun syncVisits(@Body names: HashMap<String,Any>): Response<BaseResponse>

}
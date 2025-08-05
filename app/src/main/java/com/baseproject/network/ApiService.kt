package com.baseproject.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/create/user/")
    suspend
    fun register(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<GenericResponse>

    @GET("/login/")
    suspend
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<GenericResponse>

}


data class GenericResponse(
    val statusCode: String,
    val message: String
)
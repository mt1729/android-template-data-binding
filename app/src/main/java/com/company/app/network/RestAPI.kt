package com.company.app.network

import com.company.app.database.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface RestAPI {
    @POST("/sessions")
    suspend fun login(username: String, password: String): Response<User>
}
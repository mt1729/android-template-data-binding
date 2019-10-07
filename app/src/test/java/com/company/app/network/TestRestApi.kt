package com.company.app.network

import com.company.app.database.User
import retrofit2.Response

// The intention here is to have each test override each call/function as needed
open class TestRestApi : RestApi {
    override suspend fun login(body: RestApi.LoginBody): Response<User> {
        throw NotImplementedError("Login not implemented")
    }
}

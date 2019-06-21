package com.company.app.scenes.login

import com.company.app.database.User
import com.company.app.network.RestAPI
import com.company.app.network.Result
import java.io.IOException

class LoginRepository(
    private val api: RestAPI
) {
    suspend fun login(username: String, password: String): Result<User> {
        try {
            val reqBody = RestAPI.LoginBody(username, password)
            val res = api.login(reqBody)

            res.body()?.let {
                return@login Result.Success(it)
            }

            return Result.Failure(res.code())
        } catch (err: IOException) {
            return Result.Error(err)
        }
    }
}
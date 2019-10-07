package com.company.app.scenes.login

import com.company.app.app.PreferenceStore
import com.company.app.database.User
import com.company.app.network.RestApi
import com.company.app.network.Result
import java.io.IOException

class LoginRepository(
    private val api: RestApi,
    private val prefs: PreferenceStore
) {
    suspend fun login(username: String, password: String): Result<User> {
        try {
            val reqBody = RestApi.LoginBody(username, password)

            val res = api.login(reqBody)
            val resBody = res.body()
            val resCode = res.code()

            resBody?.let { prefs.username = username }

            return when (resBody) {
                checkNotNull(resBody) -> Result.Success(resBody)
                else -> Result.Failure(resCode)
            }
        } catch (err: IOException) {
            return Result.Error(err)
        }
    }
}

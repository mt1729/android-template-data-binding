package com.company.app.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.company.app.R
import com.company.app.app.appTestModule
import com.company.app.database.User
import com.company.app.network.RestApi
import com.company.app.network.StatusCodes.BAD_REQUEST
import com.company.app.network.StatusCodes.INTERNAL_SERVER_ERROR
import com.company.app.network.StatusCodes.OK
import com.company.app.network.TestRestApi
import com.company.app.scenes.login.LoginViewModel
import com.company.app.scenes.login.loginModule
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Response

class LoginTest : KoinTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val vm by inject<LoginViewModel>()

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun loginSuccess() {
        val testUserId = "testId"

        val networkTestModule = module {
            single<RestApi> {
                object : TestRestApi() {
                    override suspend fun login(body: RestApi.LoginBody): Response<User> {
                        return Response.success(OK, User(testUserId))
                    }
                }
            }
        }
        startKoin { modules(listOf(appTestModule, networkTestModule, loginModule)) }

        vm.onUsernameChanged("testUsername")
        vm.onPasswordChanged("testPassword")
        runBlocking { vm.login().join() }

        val user = vm.loginSuccess.value!!
        assertEquals(testUserId, user.id)
    }

    @Test
    fun loginFailure() {
        val networkTestModule = module {
            single<RestApi> {
                object : TestRestApi() {
                    override suspend fun login(body: RestApi.LoginBody): Response<User> {
                        val errorResBody = ResponseBody.create(MediaType.get("application/json"), "Bad Request")
                        return Response.error(BAD_REQUEST, errorResBody)
                    }
                }
            }
        }
        startKoin { modules(listOf(appTestModule, networkTestModule, loginModule)) }

        vm.onUsernameChanged("testUsername")
        vm.onPasswordChanged("testPassword")
        runBlocking { vm.login().join() }

        assertEquals(R.string.login_failed, vm.loginFailure.value!!)
    }

    @Test
    fun loginError() {
        val networkTestModule = module {
            single<RestApi> {
                object : TestRestApi() {
                    override suspend fun login(body: RestApi.LoginBody): Response<User> {
                        val errorResBody =
                            ResponseBody.create(MediaType.get("application/json"), "Internal Server Error")
                        return Response.error(INTERNAL_SERVER_ERROR, errorResBody)
                    }
                }
            }
        }
        startKoin { modules(listOf(appTestModule, networkTestModule, loginModule)) }

        vm.onUsernameChanged("testUsername")
        vm.onPasswordChanged("testPassword")
        runBlocking { vm.login().join() }

        assertEquals(R.string.error_generic, vm.loginFailure.value!!)
    }
}
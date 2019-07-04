package com.company.app.network

import com.company.app.BuildConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val networkModule = module {
    single<OkHttpClient> { OkHttpClient.Builder().build() }
    single<RestApi> {
        return@single Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_ENDPOINT_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(RestApi::class.java)
    }
}
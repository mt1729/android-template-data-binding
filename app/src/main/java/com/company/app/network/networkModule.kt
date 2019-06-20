package com.company.app.network

import com.company.app.BuildConfig
import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val networkModule = module {
    single<RestAPI> {
        return@single Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_ENDPOINT_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(RestAPI::class.java)
    }
}
package com.company.app.app

import android.app.Application
import com.company.app.network.networkModule
import com.company.app.scenes.login.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(appModule, networkModule, loginModule)
            )
        }
    }
}
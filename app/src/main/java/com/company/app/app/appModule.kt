package com.company.app.app

import android.os.AsyncTask
import kotlinx.coroutines.asCoroutineDispatcher
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

internal val appModule = module {
    // Using THREAD_POOL_EXECUTOR for the sak of passing Espresso tests (implicit IdlingResource)
    single<CoroutineContext> { AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher() }
}
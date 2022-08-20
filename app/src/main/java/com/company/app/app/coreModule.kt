package com.company.app.app

import android.os.AsyncTask
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.asCoroutineDispatcher
import org.koin.dsl.module

internal val coreModule = module {
    // TODO: - Does Espresso still depend on this?
    // Using THREAD_POOL_EXECUTOR for the sake of passing Espresso tests (implicit IdlingResource)
    single<CoroutineContext> { AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher() }
    single<PreferenceStore> { AppPreferenceStore(get()) }
}

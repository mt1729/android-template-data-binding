package com.company.app.app

import android.os.AsyncTask
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.asCoroutineDispatcher
import org.koin.dsl.module

// TODO: - Static analyzer (detekt)
// TODO: - Add pre-commit and build hook to run ktlint
// TODO: - Dynamic feature modularization (figure out what needs to be in core)
internal val appModule = module {
    // Using THREAD_POOL_EXECUTOR for the sake of passing Espresso tests (implicit IdlingResource)
    single<CoroutineContext> { AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher() }
    single<PreferenceStore> { MyPreferenceStore(get()) }
}

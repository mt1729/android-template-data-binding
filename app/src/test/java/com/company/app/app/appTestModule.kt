package com.company.app.app

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

internal val appTestModule = module {
    // Using THREAD_POOL_EXECUTOR for the sake of passing Espresso tests (implicit IdlingResource)
    single<CoroutineContext> { Dispatchers.Default }
    single<PreferenceStore> { TestPreferenceStore() }
}
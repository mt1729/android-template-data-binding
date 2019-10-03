package com.company.app.app

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

internal val appTestModule = module {
    // Using THREAD_POOL_EXECUTOR for the sake of passing Espresso tests (implicit IdlingResource)
    single<CoroutineContext> { Dispatchers.Default }
    single<PreferenceStore> { TestPreferenceStore() }
}

package com.company.app.app

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

internal val appTestModule = module {
    single<CoroutineContext> { Dispatchers.Default }
    single<PreferenceStore> { TestPreferenceStore() }
}

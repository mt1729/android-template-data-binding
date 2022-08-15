package com.company.app.scenes.login


import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val loginModule = module {
    single { LoginRepository(get(), get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
}

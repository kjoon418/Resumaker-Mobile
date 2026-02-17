package com.resumaker.feature.login.di

import com.resumaker.feature.login.viewmodel.LoginViewModel
import com.resumaker.feature.login.viewmodel.LogoutViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureLoginModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { LogoutViewModel(get()) }
}

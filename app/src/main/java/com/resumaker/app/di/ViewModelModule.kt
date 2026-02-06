package com.resumaker.app.di

import com.resumaker.app.ui.login.LoginViewModel
import com.resumaker.app.ui.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * ViewModel 정의 모듈.
 */
val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
}

package com.resumaker.feature.signup.di

import com.resumaker.feature.signup.viewmodel.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureSignupModule = module {
    viewModel { SignUpViewModel(get()) }
}

package com.resumaker.app.di

import com.resumaker.app.ui.careermanager.CareerManagerViewModel
import com.resumaker.app.ui.login.LoginViewModel
import com.resumaker.app.ui.personamanagement.PersonaManagementViewModel
import com.resumaker.app.ui.resumeedit.ResumeEditViewModel
import com.resumaker.app.ui.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * ViewModel 정의 모듈.
 */
val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { CareerManagerViewModel(get()) }
    viewModel { PersonaManagementViewModel(get()) }
    viewModel { ResumeEditViewModel(get()) }
}

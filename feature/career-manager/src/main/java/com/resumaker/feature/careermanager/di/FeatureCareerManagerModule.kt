package com.resumaker.feature.careermanager.di

import com.resumaker.feature.careermanager.viewmodel.CareerManagerViewModel
import com.resumaker.feature.careermanager.viewmodel.PersonaManagementViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureCareerManagerModule = module {
    viewModel { CareerManagerViewModel(get()) }
    viewModel { PersonaManagementViewModel(get()) }
}

package com.resumaker.feature.mypage.di

import com.resumaker.feature.mypage.viewmodel.MyPageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureMyPageModule = module {
    viewModel { MyPageViewModel(get(), get()) }
}

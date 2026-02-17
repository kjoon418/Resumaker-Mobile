package com.resumaker.app.di

import org.koin.dsl.module

/**
 * ViewModel 정의 모듈.
 * app 모듈은 중재자 역할만 수행하므로 ViewModel은 각 feature 모듈에서 정의합니다.
 */
val viewModelModule = module {
    // ViewModels are defined in feature modules (featureCareerManagerModule, featureMyPageModule, featureResumeBuilderModule)
}

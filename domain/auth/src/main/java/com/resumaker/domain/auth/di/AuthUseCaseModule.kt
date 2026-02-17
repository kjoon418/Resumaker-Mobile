package com.resumaker.domain.auth.di

import com.resumaker.domain.auth.LogoutUseCase
import org.koin.dsl.module

val authUseCaseModule = module {
    factory { LogoutUseCase(get()) }
}

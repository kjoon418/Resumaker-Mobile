package com.resumaker.data.auth.di

import com.resumaker.data.auth.AuthRepositoryImpl
import com.resumaker.domain.auth.AuthRepository
import org.koin.dsl.module

val authRepositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}

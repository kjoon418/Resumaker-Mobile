package com.resumaker.app.di

import com.resumaker.app.data.auth.AuthRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AuthRepository(get()) }
}

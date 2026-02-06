package com.resumaker.app.di

import com.resumaker.app.data.auth.AuthApiService
import com.resumaker.app.data.persona.PersonaApiService
import com.resumaker.app.data.remote.RetrofitClient
import org.koin.dsl.module

val networkModule = module {
    single<AuthApiService> {
        RetrofitClient.createService(AuthApiService::class.java)
    }
    single<PersonaApiService> {
        RetrofitClient.createService(PersonaApiService::class.java)
    }
}

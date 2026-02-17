package com.resumaker.app.di

import com.resumaker.app.BuildConfig
import com.resumaker.core.network.RetrofitClient
import com.resumaker.data.auth.AuthApiService
import com.resumaker.data.mypage.MypageApiService
import com.resumaker.data.persona.PersonaApiService
import com.resumaker.data.resume.GenerateResumeApiService
import com.resumaker.data.resume.ParsePdfApiService
import org.koin.dsl.module

private val retrofit = RetrofitClient.create(
    baseUrl = BuildConfig.API_BASE_URL,
    debug = BuildConfig.DEBUG
)

val networkModule = module {
    single<AuthApiService> {
        RetrofitClient.createService(retrofit, AuthApiService::class.java)
    }
    single<PersonaApiService> {
        RetrofitClient.createService(retrofit, PersonaApiService::class.java)
    }
    single<MypageApiService> {
        RetrofitClient.createService(retrofit, MypageApiService::class.java)
    }
    single<ParsePdfApiService> {
        RetrofitClient.createService(retrofit, ParsePdfApiService::class.java)
    }
    single<GenerateResumeApiService> {
        RetrofitClient.createService(retrofit, GenerateResumeApiService::class.java)
    }
}

package com.resumaker.app.di

import com.resumaker.app.data.auth.AuthRepository
import com.resumaker.app.data.mypage.MypageRepository
import com.resumaker.app.data.parsepdf.ParsePdfRepository
import com.resumaker.app.data.persona.PersonaRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AuthRepository(get()) }
    single { PersonaRepository(get()) }
    single { MypageRepository(get()) }
    single { ParsePdfRepository(get()) }
}

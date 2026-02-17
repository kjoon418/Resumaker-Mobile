package com.resumaker.domain.persona.di

import com.resumaker.domain.persona.GetPersonasUseCase
import org.koin.dsl.module

val personaUseCaseModule = module {
    factory { GetPersonasUseCase(get()) }
}

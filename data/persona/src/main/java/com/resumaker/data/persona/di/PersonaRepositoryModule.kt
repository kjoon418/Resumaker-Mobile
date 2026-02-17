package com.resumaker.data.persona.di

import com.resumaker.data.persona.PersonaRepositoryImpl
import com.resumaker.domain.persona.PersonaRepository
import org.koin.dsl.module

val personaRepositoryModule = module {
    single<PersonaRepository> { PersonaRepositoryImpl(get()) }
}

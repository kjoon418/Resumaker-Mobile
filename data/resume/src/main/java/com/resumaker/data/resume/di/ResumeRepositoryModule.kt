package com.resumaker.data.resume.di

import com.resumaker.data.resume.GenerateResumeRepositoryImpl
import com.resumaker.data.resume.ParsePdfRepositoryImpl
import com.resumaker.domain.resume.GenerateResumeRepository
import com.resumaker.domain.resume.ParsePdfRepository
import org.koin.dsl.module

val resumeRepositoryModule = module {
    single<ParsePdfRepository> { ParsePdfRepositoryImpl(get()) }
    single<GenerateResumeRepository> { GenerateResumeRepositoryImpl(get()) }
}

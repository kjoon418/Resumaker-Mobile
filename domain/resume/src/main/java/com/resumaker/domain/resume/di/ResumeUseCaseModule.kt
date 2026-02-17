package com.resumaker.domain.resume.di

import com.resumaker.domain.resume.GetGeneratedResumeUseCase
import com.resumaker.domain.resume.GetLastParsedDetailUseCase
import com.resumaker.domain.resume.PrepareGenerateResumeUseCase
import org.koin.dsl.module

val resumeUseCaseModule = module {
    factory { GetGeneratedResumeUseCase(get()) }
    factory { GetLastParsedDetailUseCase(get()) }
    factory { PrepareGenerateResumeUseCase(get()) }
}

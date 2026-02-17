package com.resumaker.domain.mypage.di

import com.resumaker.domain.mypage.GetMypageUseCase
import com.resumaker.domain.mypage.UpdateMypageUseCase
import org.koin.dsl.module

val mypageUseCaseModule = module {
    factory { GetMypageUseCase(get()) }
    factory { UpdateMypageUseCase(get()) }
}

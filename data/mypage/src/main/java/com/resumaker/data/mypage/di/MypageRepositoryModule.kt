package com.resumaker.data.mypage.di

import com.resumaker.data.mypage.MypageRepositoryImpl
import com.resumaker.domain.mypage.MypageRepository
import org.koin.dsl.module

val mypageRepositoryModule = module {
    single<MypageRepository> { MypageRepositoryImpl(get()) }
}

package com.resumaker.app

import android.app.Application
import com.resumaker.app.di.networkModule
import com.resumaker.app.di.viewModelModule
import com.resumaker.core.datastore.di.datastoreModule
import com.resumaker.data.auth.di.authRepositoryModule
import com.resumaker.data.mypage.di.mypageRepositoryModule
import com.resumaker.data.persona.di.personaRepositoryModule
import com.resumaker.data.resume.di.resumeRepositoryModule
import com.resumaker.domain.auth.di.authUseCaseModule
import com.resumaker.domain.mypage.di.mypageUseCaseModule
import com.resumaker.domain.persona.di.personaUseCaseModule
import com.resumaker.domain.resume.di.resumeUseCaseModule
import com.resumaker.feature.login.di.featureLoginModule
import com.resumaker.feature.signup.di.featureSignupModule
import com.resumaker.feature.careermanager.di.featureCareerManagerModule
import com.resumaker.feature.mypage.di.featureMyPageModule
import com.resumaker.feature.resumebuilder.di.featureResumeBuilderModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ResumakerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.NONE)
            androidContext(this@ResumakerApplication)
            modules(
                networkModule,
                datastoreModule,
                authRepositoryModule,
                personaRepositoryModule,
                mypageRepositoryModule,
                resumeRepositoryModule,
                authUseCaseModule,
                personaUseCaseModule,
                mypageUseCaseModule,
                resumeUseCaseModule,
                viewModelModule,
                featureLoginModule,
                featureSignupModule,
                featureCareerManagerModule,
                featureMyPageModule,
                featureResumeBuilderModule
            )
        }
    }
}

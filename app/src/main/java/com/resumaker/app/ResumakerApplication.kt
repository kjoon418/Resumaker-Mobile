package com.resumaker.app

import android.app.Application
import com.resumaker.app.di.networkModule
import com.resumaker.app.di.repositoryModule
import com.resumaker.app.di.viewModelModule
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
                repositoryModule,
                viewModelModule
            )
        }
    }
}

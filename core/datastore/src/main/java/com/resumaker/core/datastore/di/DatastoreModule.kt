package com.resumaker.core.datastore.di

import com.resumaker.core.datastore.ResumeEditPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val datastoreModule = module {
    single { ResumeEditPreferences(androidContext()) }
}

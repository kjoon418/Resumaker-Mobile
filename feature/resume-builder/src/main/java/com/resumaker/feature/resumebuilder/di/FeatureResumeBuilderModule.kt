package com.resumaker.feature.resumebuilder.di

import com.resumaker.feature.resumebuilder.viewmodel.ResumeCompletionViewModel
import com.resumaker.feature.resumebuilder.viewmodel.ResumeDetailInputViewModel
import com.resumaker.feature.resumebuilder.viewmodel.ResumeEditViewModel
import com.resumaker.feature.resumebuilder.viewmodel.ResumeGeneratingViewModel
import com.resumaker.feature.resumebuilder.viewmodel.ResumeUploadViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureResumeBuilderModule = module {
    viewModel { ResumeUploadViewModel(get()) }
    viewModel { ResumeDetailInputViewModel(get(), get()) }
    viewModel { ResumeGeneratingViewModel(get()) }
    viewModel { ResumeCompletionViewModel(get()) }
    viewModel { ResumeEditViewModel(get(), get(), get()) }
}

package com.resumaker.feature.resumebuilder.viewmodel

import androidx.lifecycle.ViewModel
import com.resumaker.domain.resume.GenerateResumeRepository
import com.resumaker.domain.resume.model.GeneratedResume
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ResumeCompletionViewModel(
    private val generateResumeRepository: GenerateResumeRepository
) : ViewModel() {

    private val _generatedResume = MutableStateFlow<GeneratedResume?>(null)
    val generatedResume: StateFlow<GeneratedResume?> = _generatedResume.asStateFlow()

    init {
        _generatedResume.value = generateResumeRepository.getGeneratedResume()
    }
}

package com.resumaker.app.ui.resumecreate

import androidx.lifecycle.ViewModel
import com.resumaker.app.data.generate.GenerateResumeRepository
import com.resumaker.app.data.remote.dto.GenerateResumeResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 이력서 완성(4단계) 화면 ViewModel.
 * - [GenerateResumeRepository.getGeneratedResume]로 Step3에서 저장한 생성 결과를 노출.
 */
class ResumeCompletionViewModel(
    private val generateResumeRepository: GenerateResumeRepository
) : ViewModel() {

    private val _generatedResume = MutableStateFlow<GenerateResumeResponse?>(null)
    val generatedResume: StateFlow<GenerateResumeResponse?> = _generatedResume.asStateFlow()

    init {
        _generatedResume.value = generateResumeRepository.getGeneratedResume()
    }
}

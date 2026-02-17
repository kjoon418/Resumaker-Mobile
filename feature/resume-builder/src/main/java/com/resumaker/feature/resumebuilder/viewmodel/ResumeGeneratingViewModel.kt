package com.resumaker.feature.resumebuilder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resumaker.domain.common.ApiResult
import com.resumaker.domain.resume.GenerateResumeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 이력서 생성 중(3단계) 화면 ViewModel.
 */
class ResumeGeneratingViewModel(
    private val generateResumeRepository: GenerateResumeRepository
) : ViewModel() {

    private val _isGenerating = MutableStateFlow(true)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun startGeneration(onNavigateToCompletion: () -> Unit) {
        viewModelScope.launch {
            val request = generateResumeRepository.getAndClearPendingRequest()
            if (request == null) {
                _error.update { "생성할 정보가 없습니다. 이전 단계에서 입력을 완료해 주세요." }
                _isGenerating.update { false }
                return@launch
            }
            _error.update { null }
            when (val result = generateResumeRepository.generate(request)) {
                is ApiResult.Success -> {
                    generateResumeRepository.setGeneratedResume(result.data)
                    _isGenerating.update { false }
                    delay(3000L)
                    onNavigateToCompletion()
                }
                is ApiResult.Error -> {
                    _error.update { result.message }
                    _isGenerating.update { false }
                }
                is ApiResult.NetworkError -> {
                    _error.update { "네트워크 연결을 확인해 주세요." }
                    _isGenerating.update { false }
                }
            }
        }
    }
}

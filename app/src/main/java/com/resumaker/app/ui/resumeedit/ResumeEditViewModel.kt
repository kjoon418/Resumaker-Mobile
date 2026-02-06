package com.resumaker.app.ui.resumeedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resumaker.app.data.generate.GenerateResumeRepository
import com.resumaker.app.data.persona.PersonaRepository
import com.resumaker.app.data.remote.ApiResult
import com.resumaker.app.data.remote.dto.GenerateResumeResponse
import com.resumaker.app.model.Persona
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 이력서 편집 화면의 상태 및 로직.
 * - [ResumeCompletionScreen]과 동일한 생성 이력서 목 데이터([GenerateResumeRepository])를 노출.
 * - 페르소나 피드백 시트에서 면접관 페르소나 목록을 Repository로부터 조회합니다.
 */
class ResumeEditViewModel(
    private val personaRepository: PersonaRepository,
    private val generateResumeRepository: GenerateResumeRepository
) : ViewModel() {

    /** [ResumeCompletionScreen]과 동일한 생성 이력서 데이터. 미리보기·편집 폼 초기값에 사용. */
    private val _generatedResume = MutableStateFlow<GenerateResumeResponse?>(generateResumeRepository.getGeneratedResume())
    val generatedResume: StateFlow<GenerateResumeResponse?> = _generatedResume.asStateFlow()

    private val _personas = MutableStateFlow<List<Persona>>(emptyList())
    val personas: StateFlow<List<Persona>> = _personas.asStateFlow()

    private val _isLoadingPersonas = MutableStateFlow(false)
    val isLoadingPersonas: StateFlow<Boolean> = _isLoadingPersonas.asStateFlow()

    private val _personasError = MutableStateFlow<String?>(null)
    val personasError: StateFlow<String?> = _personasError.asStateFlow()

    /**
     * 서버에서 페르소나 목록을 조회합니다.
     * 면접관 페르소나 피드백 시트가 열릴 때 호출합니다.
     */
    fun loadPersonas() {
        viewModelScope.launch {
            _isLoadingPersonas.update { true }
            _personasError.update { null }
            when (val result = personaRepository.getPersonas(activeOnly = true)) {
                is ApiResult.Success -> _personas.update { result.data }
                is ApiResult.Error -> _personasError.update { result.message }
                is ApiResult.NetworkError -> _personasError.update { "네트워크 연결을 확인해 주세요." }
            }
            _isLoadingPersonas.update { false }
        }
    }
}

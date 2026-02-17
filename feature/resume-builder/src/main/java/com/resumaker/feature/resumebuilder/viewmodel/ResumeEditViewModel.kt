package com.resumaker.feature.resumebuilder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resumaker.feature.resumebuilder.contract.ResumeEditIntent
import com.resumaker.feature.resumebuilder.contract.ResumeEditState
import com.resumaker.core.datastore.ResumeEditPreferences
import com.resumaker.domain.common.ApiResult
import com.resumaker.domain.persona.GetPersonasUseCase
import com.resumaker.domain.resume.GetGeneratedResumeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 이력서 편집 화면의 MVI ViewModel.
 */
class ResumeEditViewModel(
    private val getPersonasUseCase: GetPersonasUseCase,
    private val getGeneratedResumeUseCase: GetGeneratedResumeUseCase,
    private val resumeEditPreferences: ResumeEditPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(createInitialState())
    val state: StateFlow<ResumeEditState> = _state.asStateFlow()

    private fun createInitialState(): ResumeEditState {
        val generatedResume = getGeneratedResumeUseCase()
        val items = generatedResume?.items.orEmpty()
        val (name, career, strengths, projects) = if (items.isEmpty()) {
            InitialEditValues(
                name = "김민준",
                career = "테크리드 솔루션즈 시니어 UI 디자이너 (2022~)\n• B2B SaaS 대시보드 리디자인 리드\n• 디자인 시스템 구축 및 컴포넌트 문서화",
                strengths = "React · TypeScript · 성능 최적화 · 디자인 시스템",
                projects = "B2B SaaS 대시보드 리디자인 – UI/UX 개선, 컴포넌트 라이브러리 구축"
            )
        } else {
            val position = items.find { it.subTitle == "지원 포지션" }?.content?.takeIf { it.isNotBlank() }.orEmpty()
            val careerVal = items.find { it.subTitle == "한줄 소개" }?.content?.takeIf { it.isNotBlank() }.orEmpty()
            val strengthsVal = items.find { it.subTitle == "핵심 역량" }?.content?.takeIf { it.isNotBlank() }.orEmpty()
            val projectsVal = items.find { it.subTitle == "프로젝트" }?.content?.takeIf { it.isNotBlank() }.orEmpty()
            InitialEditValues(
                name = if (position.isNotBlank()) position else "이력서 제목",
                career = if (careerVal.isNotBlank()) careerVal else "경력 요약을 입력하세요.",
                strengths = if (strengthsVal.isNotBlank()) strengthsVal else "핵심 역량을 입력하세요.",
                projects = if (projectsVal.isNotBlank()) projectsVal else "주요 프로젝트를 입력하세요."
            )
        }
        return ResumeEditState(
            generatedResume = generatedResume,
            name = name,
            career = career,
            strengths = strengths,
            projects = projects,
            showAiBubble = !resumeEditPreferences.hasVisitedResumeEdit()
        )
    }

    fun onIntent(intent: ResumeEditIntent) {
        when (intent) {
            is ResumeEditIntent.LoadPersonas -> loadPersonas()
            is ResumeEditIntent.TogglePreviewMode -> _state.update { it.copy(isPreviewMode = !it.isPreviewMode) }
            is ResumeEditIntent.ShowAiSheet -> _state.update { it.copy(showAiSheet = true) }
            is ResumeEditIntent.HideAiSheet -> _state.update { it.copy(showAiSheet = false) }
            is ResumeEditIntent.ShowAntiPatternSheet -> _state.update {
                it.copy(showAiSheet = false, showAntiPatternSheet = true)
            }
            is ResumeEditIntent.HideAntiPatternSheet -> _state.update { it.copy(showAntiPatternSheet = false) }
            is ResumeEditIntent.ShowPersonaSheet -> {
                _state.update { it.copy(showAiSheet = false, showPersonaSheet = true) }
                loadPersonas()
            }
            is ResumeEditIntent.HidePersonaSheet -> _state.update { it.copy(showPersonaSheet = false) }
            is ResumeEditIntent.DismissAiBubble -> {
                resumeEditPreferences.setVisitedResumeEdit(true)
                _state.update { it.copy(showAiBubble = false) }
            }
            is ResumeEditIntent.ClearFeedbackResult -> _state.update {
                it.copy(feedbackResultTitle = "", feedbackResultText = "")
            }
            is ResumeEditIntent.UpdateName -> _state.update { it.copy(name = intent.value) }
            is ResumeEditIntent.UpdateCareer -> _state.update { it.copy(career = intent.value) }
            is ResumeEditIntent.UpdateStrengths -> _state.update { it.copy(strengths = intent.value) }
            is ResumeEditIntent.UpdateProjects -> _state.update { it.copy(projects = intent.value) }
            is ResumeEditIntent.SelectAntiPattern -> _state.update {
                it.copy(
                    feedbackResultTitle = intent.title,
                    feedbackResultText = intent.feedbackText,
                    showAntiPatternSheet = false
                )
            }
            is ResumeEditIntent.SelectPersona -> _state.update {
                it.copy(
                    feedbackResultTitle = intent.title,
                    feedbackResultText = intent.feedbackText,
                    showPersonaSheet = false
                )
            }
        }
    }

    fun loadPersonas() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingPersonas = true, personasError = null) }
            when (val result = getPersonasUseCase(activeOnly = true)) {
                is ApiResult.Success -> _state.update { it.copy(personas = result.data, isLoadingPersonas = false) }
                is ApiResult.Error -> _state.update { it.copy(personasError = result.message, isLoadingPersonas = false) }
                is ApiResult.NetworkError -> _state.update {
                    it.copy(personasError = "네트워크 연결을 확인해 주세요.", isLoadingPersonas = false)
                }
            }
        }
    }

    private data class InitialEditValues(
        val name: String,
        val career: String,
        val strengths: String,
        val projects: String
    )
}

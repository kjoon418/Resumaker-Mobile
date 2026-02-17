package com.resumaker.feature.resumebuilder.contract

import com.resumaker.domain.persona.model.Persona
import com.resumaker.domain.resume.model.GeneratedResume

/**
 * MVI Contract for ResumeEditScreen.
 */

/** UI 상태 - viewState.copy()로 불변성 유지 */
data class ResumeEditState(
    val generatedResume: GeneratedResume? = null,
    val personas: List<Persona> = emptyList(),
    val isLoadingPersonas: Boolean = false,
    val personasError: String? = null,
    val isPreviewMode: Boolean = false,
    val showAiSheet: Boolean = false,
    val showAntiPatternSheet: Boolean = false,
    val showPersonaSheet: Boolean = false,
    val showAiBubble: Boolean = true,
    val feedbackResultTitle: String = "",
    val feedbackResultText: String = "",
    val name: String = "",
    val career: String = "",
    val strengths: String = "",
    val projects: String = ""
) {
    val items get() = generatedResume?.items.orEmpty()
}

/** Intent(Action) - UI에서 발생하는 사용자 의도 */
sealed interface ResumeEditIntent {
    data object LoadPersonas : ResumeEditIntent
    data object TogglePreviewMode : ResumeEditIntent
    data object ShowAiSheet : ResumeEditIntent
    data object HideAiSheet : ResumeEditIntent
    data object ShowAntiPatternSheet : ResumeEditIntent
    data object HideAntiPatternSheet : ResumeEditIntent
    data object ShowPersonaSheet : ResumeEditIntent
    data object HidePersonaSheet : ResumeEditIntent
    data object DismissAiBubble : ResumeEditIntent
    data object ClearFeedbackResult : ResumeEditIntent
    data class UpdateName(val value: String) : ResumeEditIntent
    data class UpdateCareer(val value: String) : ResumeEditIntent
    data class UpdateStrengths(val value: String) : ResumeEditIntent
    data class UpdateProjects(val value: String) : ResumeEditIntent
    data class SelectAntiPattern(val title: String, val feedbackText: String) : ResumeEditIntent
    data class SelectPersona(val title: String, val feedbackText: String) : ResumeEditIntent
}

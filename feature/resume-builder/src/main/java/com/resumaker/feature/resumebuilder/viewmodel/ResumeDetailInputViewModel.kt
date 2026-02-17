package com.resumaker.feature.resumebuilder.viewmodel

import androidx.lifecycle.ViewModel
import com.resumaker.feature.resumebuilder.contract.ResumeDetailInputIntent
import com.resumaker.feature.resumebuilder.contract.ResumeDetailInputState
import com.resumaker.domain.resume.model.ProjectHistoryItem
import com.resumaker.domain.resume.GetLastParsedDetailUseCase
import com.resumaker.domain.resume.PrepareGenerateResumeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * 이력서 상세 입력(2단계) 화면의 MVI ViewModel.
 */
class ResumeDetailInputViewModel(
    private val getLastParsedDetailUseCase: GetLastParsedDetailUseCase,
    private val prepareGenerateResumeUseCase: PrepareGenerateResumeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(createInitialState())
    val state: StateFlow<ResumeDetailInputState> = _state.asStateFlow()

    private fun createInitialState(): ResumeDetailInputState {
        val initial = getLastParsedDetailUseCase()
        return if (initial != null) {
            ResumeDetailInputState(
                resumeFormat = initial.resumeFormat,
                targetRole = initial.targetRole,
                slogan = initial.headline,
                strengthKeywords = initial.strengthKeywords,
                projectHistoryItems = initial.projects,
                collaborationStyle = initial.collaborationStyle,
                techStacks = initial.mainTechStack,
                futureGoals = initial.futureGoal,
                wasPrefilledFromPdf = true
            )
        } else {
            ResumeDetailInputState()
        }
    }

    fun onIntent(intent: ResumeDetailInputIntent) {
        when (intent) {
            is ResumeDetailInputIntent.SetResumeFormat -> _state.update { it.copy(resumeFormat = intent.value) }
            is ResumeDetailInputIntent.SetTargetRole -> _state.update { it.copy(targetRole = intent.value) }
            is ResumeDetailInputIntent.SetSlogan -> _state.update { it.copy(slogan = intent.value) }
            is ResumeDetailInputIntent.SetCollaborationStyle -> _state.update { it.copy(collaborationStyle = intent.value) }
            is ResumeDetailInputIntent.SetFutureGoals -> _state.update { it.copy(futureGoals = intent.value) }
            is ResumeDetailInputIntent.SetStrengthKeywords -> _state.update { it.copy(strengthKeywords = intent.list) }
            is ResumeDetailInputIntent.RemoveStrengthKeyword -> _state.update {
                it.copy(strengthKeywords = it.strengthKeywords.filter { k -> k != intent.item })
            }
            is ResumeDetailInputIntent.AddStrengthKeyword -> _state.update {
                it.copy(strengthKeywords = it.strengthKeywords + intent.keyword)
            }
            is ResumeDetailInputIntent.SetProjectHistoryItems -> _state.update {
                it.copy(projectHistoryItems = intent.list)
            }
            is ResumeDetailInputIntent.RemoveProjectHistoryItem -> _state.update {
                it.copy(projectHistoryItems = it.projectHistoryItems.filter { i -> i.id != intent.item.id })
            }
            is ResumeDetailInputIntent.AddProjectHistoryItem -> _state.update {
                it.copy(projectHistoryItems = it.projectHistoryItems + intent.item)
            }
            is ResumeDetailInputIntent.SetTechStacks -> _state.update { it.copy(techStacks = intent.list) }
            is ResumeDetailInputIntent.RemoveTechStack -> _state.update {
                it.copy(techStacks = it.techStacks.filter { t -> t != intent.item })
            }
            is ResumeDetailInputIntent.AddTechStack -> _state.update {
                it.copy(techStacks = it.techStacks + intent.tech)
            }
            is ResumeDetailInputIntent.SetExtraItems -> _state.update { it.copy(extraItems = intent.list) }
            is ResumeDetailInputIntent.RemoveExtraItem -> _state.update {
                it.copy(extraItems = it.extraItems.filter { i -> i.id != intent.item.id })
            }
            is ResumeDetailInputIntent.AddExtraItem -> _state.update {
                it.copy(extraItems = it.extraItems + intent.item)
            }
            is ResumeDetailInputIntent.PrepareForGenerate -> {
                val s = _state.value
                prepareGenerateResumeUseCase(
                    resumeFormat = s.resumeFormat,
                    targetRole = s.targetRole,
                    slogan = s.slogan,
                    strengthKeywords = s.strengthKeywords,
                    projectHistoryItems = s.projectHistoryItems,
                    collaborationStyle = s.collaborationStyle,
                    techStacks = s.techStacks,
                    futureGoals = s.futureGoals
                )
            }
        }
    }

    fun prepareForGenerate(onNavigate: () -> Unit) {
        onIntent(ResumeDetailInputIntent.PrepareForGenerate)
        onNavigate()
    }

    fun setResumeFormat(value: String) = onIntent(ResumeDetailInputIntent.SetResumeFormat(value))
    fun setTargetRole(value: String) = onIntent(ResumeDetailInputIntent.SetTargetRole(value))
    fun setSlogan(value: String) = onIntent(ResumeDetailInputIntent.SetSlogan(value))
    fun setCollaborationStyle(value: String) = onIntent(ResumeDetailInputIntent.SetCollaborationStyle(value))
    fun setFutureGoals(value: String) = onIntent(ResumeDetailInputIntent.SetFutureGoals(value))
    fun setStrengthKeywords(list: List<String>) = onIntent(ResumeDetailInputIntent.SetStrengthKeywords(list))
    fun removeStrengthKeyword(item: String) = onIntent(ResumeDetailInputIntent.RemoveStrengthKeyword(item))
    fun addStrengthKeyword(keyword: String) = onIntent(ResumeDetailInputIntent.AddStrengthKeyword(keyword))
    fun setProjectHistoryItems(list: List<ProjectHistoryItem>) = onIntent(ResumeDetailInputIntent.SetProjectHistoryItems(list))
    fun removeProjectHistoryItem(item: ProjectHistoryItem) = onIntent(ResumeDetailInputIntent.RemoveProjectHistoryItem(item))
    fun addProjectHistoryItem(item: ProjectHistoryItem) = onIntent(ResumeDetailInputIntent.AddProjectHistoryItem(item))
    fun setTechStacks(list: List<String>) = onIntent(ResumeDetailInputIntent.SetTechStacks(list))
    fun removeTechStack(item: String) = onIntent(ResumeDetailInputIntent.RemoveTechStack(item))
    fun addTechStack(tech: String) = onIntent(ResumeDetailInputIntent.AddTechStack(tech))
    fun setExtraItems(list: List<com.resumaker.core.common.model.ExtraInfoItem>) = onIntent(ResumeDetailInputIntent.SetExtraItems(list))
    fun removeExtraItem(item: com.resumaker.core.common.model.ExtraInfoItem) = onIntent(ResumeDetailInputIntent.RemoveExtraItem(item))
    fun addExtraItem(item: com.resumaker.core.common.model.ExtraInfoItem) = onIntent(ResumeDetailInputIntent.AddExtraItem(item))
}

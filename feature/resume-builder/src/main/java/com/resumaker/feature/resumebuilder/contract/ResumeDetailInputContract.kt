package com.resumaker.feature.resumebuilder.contract

import com.resumaker.core.common.model.ExtraInfoItem
import com.resumaker.domain.resume.model.ProjectHistoryItem

/**
 * MVI Contract for ResumeDetailInputScreen.
 */

/** UI 상태 - viewState.copy()로 불변성 유지 */
data class ResumeDetailInputState(
    val resumeFormat: String = "",
    val targetRole: String = "",
    val slogan: String = "",
    val strengthKeywords: List<String> = emptyList(),
    val projectHistoryItems: List<ProjectHistoryItem> = emptyList(),
    val collaborationStyle: String = "",
    val techStacks: List<String> = emptyList(),
    val futureGoals: String = "",
    val extraItems: List<ExtraInfoItem> = emptyList(),
    val wasPrefilledFromPdf: Boolean = false
)

/** Intent(Action) - UI에서 발생하는 사용자 의도 */
sealed interface ResumeDetailInputIntent {
    data class SetResumeFormat(val value: String) : ResumeDetailInputIntent
    data class SetTargetRole(val value: String) : ResumeDetailInputIntent
    data class SetSlogan(val value: String) : ResumeDetailInputIntent
    data class SetCollaborationStyle(val value: String) : ResumeDetailInputIntent
    data class SetFutureGoals(val value: String) : ResumeDetailInputIntent
    data class SetStrengthKeywords(val list: List<String>) : ResumeDetailInputIntent
    data class RemoveStrengthKeyword(val item: String) : ResumeDetailInputIntent
    data class AddStrengthKeyword(val keyword: String) : ResumeDetailInputIntent
    data class SetProjectHistoryItems(val list: List<ProjectHistoryItem>) : ResumeDetailInputIntent
    data class RemoveProjectHistoryItem(val item: ProjectHistoryItem) : ResumeDetailInputIntent
    data class AddProjectHistoryItem(val item: ProjectHistoryItem) : ResumeDetailInputIntent
    data class SetTechStacks(val list: List<String>) : ResumeDetailInputIntent
    data class RemoveTechStack(val item: String) : ResumeDetailInputIntent
    data class AddTechStack(val tech: String) : ResumeDetailInputIntent
    data class SetExtraItems(val list: List<ExtraInfoItem>) : ResumeDetailInputIntent
    data class RemoveExtraItem(val item: ExtraInfoItem) : ResumeDetailInputIntent
    data class AddExtraItem(val item: ExtraInfoItem) : ResumeDetailInputIntent
    data object PrepareForGenerate : ResumeDetailInputIntent
}

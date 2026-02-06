package com.resumaker.app.ui.resumecreate

import androidx.lifecycle.ViewModel
import com.resumaker.app.data.parsepdf.ParsePdfRepository
import com.resumaker.app.model.ExtraInfoItem
import com.resumaker.app.model.ParsedResumeDetail
import com.resumaker.app.model.ProjectHistoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * 이력서 상세 입력(2단계) 화면 ViewModel.
 * - 초기값: [ParsePdfRepository.getLastParsedDetail]에서 한 번 읽어 채우고, PDF 미제출 시 빈 상태.
 * - 폼 필드 상태 보관 및 업데이트 메서드 제공.
 */
class ResumeDetailInputViewModel(
    private val parsePdfRepository: ParsePdfRepository
) : ViewModel() {

    private val _resumeFormat = MutableStateFlow("")
    val resumeFormat: StateFlow<String> = _resumeFormat.asStateFlow()

    private val _targetRole = MutableStateFlow("")
    val targetRole: StateFlow<String> = _targetRole.asStateFlow()

    private val _slogan = MutableStateFlow("")
    val slogan: StateFlow<String> = _slogan.asStateFlow()

    private val _strengthKeywords = MutableStateFlow<List<String>>(emptyList())
    val strengthKeywords: StateFlow<List<String>> = _strengthKeywords.asStateFlow()

    private val _projectHistoryItems = MutableStateFlow<List<ProjectHistoryItem>>(emptyList())
    val projectHistoryItems: StateFlow<List<ProjectHistoryItem>> = _projectHistoryItems.asStateFlow()

    private val _collaborationStyle = MutableStateFlow("")
    val collaborationStyle: StateFlow<String> = _collaborationStyle.asStateFlow()

    private val _techStacks = MutableStateFlow<List<String>>(emptyList())
    val techStacks: StateFlow<List<String>> = _techStacks.asStateFlow()

    private val _futureGoals = MutableStateFlow("")
    val futureGoals: StateFlow<String> = _futureGoals.asStateFlow()

    private val _extraItems = MutableStateFlow<List<ExtraInfoItem>>(emptyList())
    val extraItems: StateFlow<List<ExtraInfoItem>> = _extraItems.asStateFlow()

    /** PDF 파싱으로 채워진 상태인지. true면 InfoBanner 표시. */
    private val _wasPrefilledFromPdf = MutableStateFlow(false)
    val wasPrefilledFromPdf: StateFlow<Boolean> = _wasPrefilledFromPdf.asStateFlow()

    init {
        val initial = parsePdfRepository.getLastParsedDetail()
        if (initial != null) {
            applyParsedDetail(initial)
            _wasPrefilledFromPdf.value = true
            parsePdfRepository.clearLastParsedDetail()
        }
    }

    private fun applyParsedDetail(detail: ParsedResumeDetail) {
        _resumeFormat.value = detail.resumeFormat
        _targetRole.value = detail.targetRole
        _slogan.value = detail.headline
        _strengthKeywords.value = detail.strengthKeywords
        _projectHistoryItems.value = detail.projects
        _collaborationStyle.value = detail.collaborationStyle
        _techStacks.value = detail.mainTechStack
        _futureGoals.value = detail.futureGoal
    }

    fun setResumeFormat(value: String) { _resumeFormat.value = value }
    fun setTargetRole(value: String) { _targetRole.value = value }
    fun setSlogan(value: String) { _slogan.value = value }
    fun setCollaborationStyle(value: String) { _collaborationStyle.value = value }
    fun setFutureGoals(value: String) { _futureGoals.value = value }

    fun setStrengthKeywords(list: List<String>) { _strengthKeywords.value = list }
    fun removeStrengthKeyword(item: String) { _strengthKeywords.update { it.filter { k -> k != item } } }
    fun addStrengthKeyword(keyword: String) { _strengthKeywords.update { it + keyword } }

    fun setProjectHistoryItems(list: List<ProjectHistoryItem>) { _projectHistoryItems.value = list }
    fun removeProjectHistoryItem(item: ProjectHistoryItem) { _projectHistoryItems.update { it.filter { i -> i.id != item.id } } }
    fun addProjectHistoryItem(item: ProjectHistoryItem) { _projectHistoryItems.update { it + item } }

    fun setTechStacks(list: List<String>) { _techStacks.value = list }
    fun removeTechStack(item: String) { _techStacks.update { it.filter { t -> t != item } } }
    fun addTechStack(tech: String) { _techStacks.update { it + tech } }

    fun setExtraItems(list: List<ExtraInfoItem>) { _extraItems.value = list }
    fun removeExtraItem(item: ExtraInfoItem) { _extraItems.update { it.filter { i -> i.id != item.id } } }
    fun addExtraItem(item: ExtraInfoItem) { _extraItems.update { it + item } }
}

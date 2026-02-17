package com.resumaker.data.resume

import com.resumaker.domain.common.ApiResult
import com.resumaker.domain.resume.model.ParsedResumeDetail
import com.resumaker.domain.resume.model.ProjectHistoryItem
import com.resumaker.data.resume.dto.ParsePdfProject
import com.resumaker.data.resume.dto.ParsePdfResponse
import com.resumaker.domain.resume.ParsePdfRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

class ParsePdfRepositoryImpl(
    private val api: ParsePdfApiService
) : ParsePdfRepository {

    private var _lastParsedDetail: ParsedResumeDetail? = null

    override fun getLastParsedDetail(): ParsedResumeDetail? = _lastParsedDetail

    override fun setLastParsedDetail(detail: ParsedResumeDetail) {
        _lastParsedDetail = detail
    }

    override fun clearLastParsedDetail() {
        _lastParsedDetail = null
    }

    override suspend fun parsePdf(file: File): ApiResult<ParsedResumeDetail> = withContext(Dispatchers.IO) {
        val detail = ParsedResumeDetail(
            resumeFormat = "기능 중심",
            targetRole = "프론트엔드 개발자",
            headline = "3년 차 프론트엔드 개발자, React와 사용자 경험에 강점",
            strengthKeywords = listOf("React", "TypeScript", "성능 최적화", "디자인 시스템"),
            projects = listOf(
                ProjectHistoryItem(
                    id = UUID.randomUUID().toString(),
                    projectName = "B2B SaaS 대시보드 리디자인",
                    keyTasks = "• 대시보드 UI/UX 개선\n• 컴포넌트 라이브러리 구축\n기술 스택: React, TypeScript, Storybook"
                )
            ),
            collaborationStyle = "애자일 스프린트, 디자이너·백엔드와 협업",
            mainTechStack = listOf("React", "TypeScript", "Next.js", "Tailwind CSS"),
            futureGoal = "시니어 프론트엔드로 성장하여 팀 기술 방향을 이끌고 싶습니다."
        )
        _lastParsedDetail = detail
        ApiResult.Success(detail)
    }
}

private fun ParsePdfResponse.toParsedResumeDetail(): ParsedResumeDetail = ParsedResumeDetail(
    resumeFormat = resumeFormat.orEmpty(),
    targetRole = targetRole.orEmpty(),
    headline = headline.orEmpty(),
    strengthKeywords = strengthKeywords.orEmpty(),
    projects = (projects.orEmpty()).map { it.toProjectHistoryItem() },
    collaborationStyle = collaborationStyle.orEmpty(),
    mainTechStack = mainTechStack.orEmpty(),
    futureGoal = futureGoal.orEmpty()
)

private fun ParsePdfProject.toProjectHistoryItem(): ProjectHistoryItem {
    val keyTasks = buildString {
        bullets?.filter { it.isNotBlank() }?.forEachIndexed { i, line ->
            if (i > 0) append("\n")
            append("• $line")
        }
        if (!techStack.isNullOrEmpty()) {
            if (isNotEmpty()) append("\n")
            append("기술 스택: ${techStack.joinToString(", ")}")
        }
    }
    return ProjectHistoryItem(
        id = UUID.randomUUID().toString(),
        projectName = title.orEmpty(),
        keyTasks = keyTasks.trim()
    )
}

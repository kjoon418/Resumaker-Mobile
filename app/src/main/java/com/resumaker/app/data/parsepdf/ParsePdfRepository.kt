package com.resumaker.app.data.parsepdf

import com.resumaker.app.data.remote.ApiResult
import com.resumaker.app.data.remote.dto.ParsePdfProject
import com.resumaker.app.data.remote.dto.ParsePdfResponse
import com.resumaker.app.model.ParsedResumeDetail
import com.resumaker.app.model.ProjectHistoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
// [임시 Mock] import okhttp3.MediaType.Companion.toMediaTypeOrNull
// import okhttp3.MultipartBody
// import okhttp3.RequestBody.Companion.asRequestBody
// import retrofit2.HttpException
import java.io.File
import java.util.UUID

/**
 * 이력서 PDF 파싱 API 진입점.
 * - parsePdf: 서버에 PDF 업로드 후 파싱 결과 반환
 * - lastParsedDetail: 다음 화면(ResumeDetailInputScreen)에 전달할 초기값 보관.
 *   PDF 제출 후 다음 단계로 이동 시 Detail ViewModel이 이 값을 읽고 초기화 후 clear.
 */
class ParsePdfRepository(
    private val api: ParsePdfApiService
) {

    private var _lastParsedDetail: ParsedResumeDetail? = null

    /** PDF 제출 후 다음 단계에서 한 번 읽어갈 초기값. 읽은 쪽에서 [clearLastParsedDetail] 호출 권장. */
    fun getLastParsedDetail(): ParsedResumeDetail? = _lastParsedDetail

    fun setLastParsedDetail(detail: ParsedResumeDetail) {
        _lastParsedDetail = detail
    }

    fun clearLastParsedDetail() {
        _lastParsedDetail = null
    }

    /**
     * 이력서 PDF 파일을 업로드하고, 서버에서 추출·분석한 폼 자동 채우기용 데이터를 반환합니다.
     * [임시] API 호출 대신 Mock [ParsedResumeDetail] 반환 후 Step2에서 폼 자동 채움.
     */
    suspend fun parsePdf(file: File): ApiResult<ParsedResumeDetail> = withContext(Dispatchers.IO) {
        // try {
        //     val requestFile = file.asRequestBody("application/pdf".toMediaTypeOrNull())
        //     val part = MultipartBody.Part.createFormData("file", file.name, requestFile)
        //     val response = api.parsePdf(part)
        //     val detail = response.toParsedResumeDetail()
        //     _lastParsedDetail = detail
        //     ApiResult.Success(detail)
        // } catch (e: HttpException) { ... } catch (e: Exception) { ... }
        val detail = ParsedResumeDetail(
            resumeFormat = "기능 중심",
            targetRole = "프론트엔드 개발자",
            headline = "3년 차 프론트엔드 개발자, React와 사용자 경험에 강점",
            strengthKeywords = listOf("React", "TypeScript", "성능 최적화", "디자인 시스템"),
            projects = listOf(
                ProjectHistoryItem(
                    id = java.util.UUID.randomUUID().toString(),
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

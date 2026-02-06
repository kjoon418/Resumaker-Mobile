package com.resumaker.app.data.parsepdf

import com.resumaker.app.data.remote.ApiResult
import com.resumaker.app.data.remote.dto.ParsePdfProject
import com.resumaker.app.data.remote.dto.ParsePdfResponse
import com.resumaker.app.model.ParsedResumeDetail
import com.resumaker.app.model.ProjectHistoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
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
     * @param file 이력서 PDF 파일
     * @return 성공 시 [ParsedResumeDetail], 실패 시 [ApiResult.Error] 또는 [ApiResult.NetworkError]
     */
    suspend fun parsePdf(file: File): ApiResult<ParsedResumeDetail> = withContext(Dispatchers.IO) {
        try {
            val requestFile = file.asRequestBody("application/pdf".toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val response = api.parsePdf(part)
            val detail = response.toParsedResumeDetail()
            _lastParsedDetail = detail
            ApiResult.Success(detail)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val message = errorBody?.takeIf { it.isNotBlank() } ?: "PDF 파싱에 실패했습니다. (${e.code()})"
            ApiResult.Error(message = message, code = e.code())
        } catch (e: Exception) {
            when (e) {
                is java.io.IOException -> ApiResult.NetworkError
                else -> ApiResult.Error(message = e.message ?: "PDF 파싱 중 오류가 발생했습니다.")
            }
        }
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

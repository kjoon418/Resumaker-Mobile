package com.resumaker.app.data.generate

import com.resumaker.app.data.remote.ApiResult
import com.resumaker.app.data.remote.dto.GeneratedResumeItem
import com.resumaker.app.data.remote.dto.GenerateResumeRequest
import com.resumaker.app.data.remote.dto.GenerateResumeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
// import retrofit2.HttpException
// import java.io.IOException

/** [임시 Mock] 이력서 생성 결과 목 데이터 */
private fun mockGenerateResumeResponse(): GenerateResumeResponse = GenerateResumeResponse(
    resumeId = 1,
    createdAt = "2025.02.07T10:00:00",
    updatedAt = "2025.02.07T10:00:00",
    itemList = null,
    items = listOf(
        GeneratedResumeItem(1, "TITLED", "지원 포지션", "프론트엔드 개발자"),
        GeneratedResumeItem(2, "TITLED", "한줄 소개", "3년 차 프론트엔드 개발자, React와 사용자 경험에 강점을 가지고 있습니다."),
        GeneratedResumeItem(3, "TITLED", "핵심 역량", "React · TypeScript · 성능 최적화 · 디자인 시스템"),
        GeneratedResumeItem(4, "TITLED", "프로젝트", "B2B SaaS 대시보드 리디자인 – 대시보드 UI/UX 개선, 컴포넌트 라이브러리 구축 (React, TypeScript, Storybook)"),
        GeneratedResumeItem(5, "SIMPLE", null, "협업 스타일: 애자일 스프린트, 디자이너·백엔드와 긴밀히 협업"),
        GeneratedResumeItem(6, "SIMPLE", null, "앞으로의 목표: 시니어 프론트엔드로 성장하여 팀 기술 방향을 이끌고 싶습니다.")
    )
)

/**
 * 이력서 생성 API 진입점.
 * - [setPendingRequest]: Step2에서 "다음 단계로" 시 폼 데이터 저장. Step3에서 [getAndClearPendingRequest]로 읽어 API 호출.
 * - [setGeneratedResume] / [getGeneratedResume]: Step3 API 성공 시 응답 저장. Step4에서 화면 렌더링용으로 읽음.
 */
class GenerateResumeRepository(
    private val api: GenerateResumeApiService
) {

    private var _pendingRequest: GenerateResumeRequest? = null
    private var _generatedResume: GenerateResumeResponse? = null

    fun setPendingRequest(request: GenerateResumeRequest) {
        _pendingRequest = request
        _generatedResume = null
    }

    fun getAndClearPendingRequest(): GenerateResumeRequest? {
        return _pendingRequest.also { _pendingRequest = null }
    }

    fun setGeneratedResume(response: GenerateResumeResponse) {
        _generatedResume = response
    }

    fun getGeneratedResume(): GenerateResumeResponse? = _generatedResume

    /**
     * [임시] API 호출 대신 Mock [GenerateResumeResponse] 반환.
     */
    suspend fun generate(request: GenerateResumeRequest): ApiResult<GenerateResumeResponse> =
        withContext(Dispatchers.IO) {
            // try {
            //     val response = api.generate(request)
            //     ApiResult.Success(response)
            // } catch (e: HttpException) { ... } catch (e: IOException) { ApiResult.NetworkError }
            ApiResult.Success(mockGenerateResumeResponse())
        }
}

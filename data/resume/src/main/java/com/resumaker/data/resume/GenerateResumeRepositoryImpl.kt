package com.resumaker.data.resume

import com.resumaker.domain.common.ApiResult
import com.resumaker.data.resume.dto.GenerateResumeResponse
import com.resumaker.data.resume.dto.GeneratedResumeItem
import com.resumaker.domain.resume.GenerateResumeRepository
import com.resumaker.domain.resume.model.GeneratedResume
import com.resumaker.domain.resume.model.GenerateResumeParams
import com.resumaker.domain.resume.model.GenerateResumeProjectItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

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

class GenerateResumeRepositoryImpl(
    private val api: GenerateResumeApiService
) : GenerateResumeRepository {

    private var _pendingRequest: GenerateResumeParams? = null
    private var _generatedResume: GeneratedResume? = null

    override fun setPendingRequest(params: GenerateResumeParams) {
        _pendingRequest = params
        _generatedResume = null
    }

    override fun getAndClearPendingRequest(): GenerateResumeParams? =
        _pendingRequest.also { _pendingRequest = null }

    override fun setGeneratedResume(resume: GeneratedResume) {
        _generatedResume = resume
    }

    override fun getGeneratedResume(): GeneratedResume? = _generatedResume

    override suspend fun generate(params: GenerateResumeParams): ApiResult<GeneratedResume> =
        withContext(Dispatchers.IO) {
            val response = mockGenerateResumeResponse()
            val domain = response.toDomain()
            ApiResult.Success(domain)
        }
}

private fun GenerateResumeResponse.toDomain(): GeneratedResume = GeneratedResume(
    resumeId = resumeId,
    createdAt = createdAt,
    updatedAt = updatedAt,
    itemList = itemList,
    items = items?.map { it.toDomain() }
)

private fun GeneratedResumeItem.toDomain() = com.resumaker.domain.resume.model.GeneratedResumeItem(
    elementId = elementId,
    type = type,
    subTitle = subTitle,
    content = content
)

package com.resumaker.domain.resume

import com.resumaker.domain.common.ApiResult
import com.resumaker.domain.resume.model.GeneratedResume
import com.resumaker.domain.resume.model.GenerateResumeParams

/**
 * 이력서 생성 Repository 인터페이스.
 */
interface GenerateResumeRepository {
    fun setPendingRequest(params: GenerateResumeParams)
    fun getAndClearPendingRequest(): GenerateResumeParams?
    fun setGeneratedResume(resume: GeneratedResume)
    fun getGeneratedResume(): GeneratedResume?
    suspend fun generate(params: GenerateResumeParams): ApiResult<GeneratedResume>
}

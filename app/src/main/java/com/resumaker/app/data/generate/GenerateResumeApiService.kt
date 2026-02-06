package com.resumaker.app.data.generate

import com.resumaker.app.data.remote.dto.GenerateResumeRequest
import com.resumaker.app.data.remote.dto.GenerateResumeResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 이력서 생성 API.
 * POST generate/ — 입력 정보로 이력서 생성.
 */
interface GenerateResumeApiService {

    @POST("api/resume/generate/")
    suspend fun generate(@Body body: GenerateResumeRequest): GenerateResumeResponse
}

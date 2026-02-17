package com.resumaker.data.resume

import com.resumaker.data.resume.dto.GenerateResumeRequest
import com.resumaker.data.resume.dto.GenerateResumeResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GenerateResumeApiService {

    @POST("api/resume/generate/")
    suspend fun generate(@Body body: GenerateResumeRequest): GenerateResumeResponse
}

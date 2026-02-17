package com.resumaker.data.mypage

import com.resumaker.data.mypage.dto.MypageResponse
import com.resumaker.data.mypage.dto.MypageUpdateRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface MypageApiService {

    @GET("api/resume/mypage")
    suspend fun getMypage(): MypageResponse

    @PUT("api/resume/mypage")
    suspend fun updateMypage(@Body body: MypageUpdateRequest): MypageResponse
}

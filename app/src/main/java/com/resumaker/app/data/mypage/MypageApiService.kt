package com.resumaker.app.data.mypage

import com.resumaker.app.data.remote.dto.mypage.MypageResponse
import com.resumaker.app.data.remote.dto.mypage.MypageUpdateRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

/**
 * 마이페이지(이력서 정보) 관련 API 정의.
 */
interface MypageApiService {

    /**
     * 마이페이지 정보 조회.
     * GET /api/resume/mypage
     */
    @GET("api/resume/mypage")
    suspend fun getMypage(): MypageResponse

    /**
     * 마이페이지 정보 수정.
     * PUT /api/resume/mypage
     *
     * @param body 수정할 학력/수상/자격증/경력 목록 (각 항목에 id 없음)
     * @return 수정 반영 후 전체 마이페이지 정보 (id 포함)
     */
    @PUT("api/resume/mypage")
    suspend fun updateMypage(@Body body: MypageUpdateRequest): MypageResponse
}

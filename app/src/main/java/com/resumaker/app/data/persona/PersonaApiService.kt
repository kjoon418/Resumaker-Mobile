package com.resumaker.app.data.persona

import com.resumaker.app.data.remote.dto.CreatePersonaRequest
import com.resumaker.app.data.remote.dto.PersonaDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 페르소나 관련 API 정의.
 */
interface PersonaApiService {

    /**
     * 페르소나 목록 조회.
     * GET /api/personas/
     * 작업 ID: personas_list
     *
     * @param activeOnly 활성화된 페르소나만 조회 (optional)
     * @param customOnly 커스텀 페르소나만 조회 (optional)
     * @param defaultOnly 기본 페르소나만 조회 (optional)
     */
    @GET("api/personas/")
    suspend fun getPersonas(
        @Query("active_only") activeOnly: Boolean? = null,
        @Query("custom_only") customOnly: Boolean? = null,
        @Query("default_only") defaultOnly: Boolean? = null
    ): List<PersonaDto>

    /**
     * 새 페르소나 생성.
     * POST /api/personas/
     * 작업 ID: personas_create
     * 생성된 페르소나 전체 정보(200) 반환.
     */
    @POST("api/personas/")
    suspend fun createPersona(@Body body: CreatePersonaRequest): PersonaDto

    /**
     * 페르소나 수정.
     * PUT /api/personas/{id}/
     * 작업 ID: personas_update
     * 커스텀 페르소나만 수정 가능. 수정된 페르소나 전체 정보(200) 반환.
     *
     * @param id 수정할 페르소나의 고유 식별자 (Path)
     */
    @PUT("api/personas/{id}/")
    suspend fun updatePersona(
        @Path("id") id: Int,
        @Body body: CreatePersonaRequest
    ): PersonaDto

    /**
     * 페르소나 삭제.
     * DELETE /api/personas/{id}/
     * 요청 파라미터 없음. 커스텀 페르소나만 삭제 가능.
     *
     * @param id 삭제할 페르소나의 고유 식별자 (Path)
     */
    @DELETE("api/personas/{id}/")
    suspend fun deletePersona(@Path("id") id: Int)
}

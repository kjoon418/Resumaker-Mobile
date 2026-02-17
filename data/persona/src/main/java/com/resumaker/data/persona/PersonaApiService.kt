package com.resumaker.data.persona

import com.resumaker.data.persona.dto.CreatePersonaRequest
import com.resumaker.data.persona.dto.PersonaDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonaApiService {

    @GET("api/personas/")
    suspend fun getPersonas(
        @Query("active_only") activeOnly: Boolean? = null,
        @Query("custom_only") customOnly: Boolean? = null,
        @Query("default_only") defaultOnly: Boolean? = null
    ): List<PersonaDto>

    @POST("api/personas/")
    suspend fun createPersona(@Body body: CreatePersonaRequest): PersonaDto

    @PUT("api/personas/{id}/")
    suspend fun updatePersona(
        @Path("id") id: Int,
        @Body body: CreatePersonaRequest
    ): PersonaDto

    @DELETE("api/personas/{id}/")
    suspend fun deletePersona(@Path("id") id: Int)
}

package com.resumaker.domain.persona

import com.resumaker.domain.common.ApiResult
import com.resumaker.domain.persona.model.Persona

/**
 * 페르소나 Repository 인터페이스.
 */
interface PersonaRepository {
    suspend fun getPersonas(
        activeOnly: Boolean = false,
        customOnly: Boolean = false,
        defaultOnly: Boolean = false
    ): ApiResult<List<Persona>>

    suspend fun createPersona(
        name: String,
        description: String,
        prompt: String,
        isActive: Boolean = true
    ): ApiResult<Persona>

    suspend fun updatePersona(
        id: Int,
        name: String,
        description: String,
        prompt: String,
        isActive: Boolean = true
    ): ApiResult<Persona>

    suspend fun deletePersona(id: Int): ApiResult<Unit>
}

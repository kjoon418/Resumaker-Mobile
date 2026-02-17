package com.resumaker.domain.persona

import com.resumaker.domain.common.ApiResult
import com.resumaker.domain.persona.model.Persona

class GetPersonasUseCase(
    private val personaRepository: PersonaRepository
) {
    suspend operator fun invoke(
        activeOnly: Boolean = false,
        customOnly: Boolean = false,
        defaultOnly: Boolean = false
    ): ApiResult<List<Persona>> = personaRepository.getPersonas(
        activeOnly = activeOnly,
        customOnly = customOnly,
        defaultOnly = defaultOnly
    )
}

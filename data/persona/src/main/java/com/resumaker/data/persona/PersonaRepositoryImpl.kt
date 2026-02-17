package com.resumaker.data.persona

import com.resumaker.domain.common.ApiResult
import com.resumaker.domain.persona.model.Persona
import com.resumaker.domain.persona.PersonaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val mockPersonas = mutableListOf(
    Persona("1", "친절한 면접관", "많은 피드백을 주며 대화를 이끌어 줍니다.", "persona1", "당신은 친절하고 격려하는 면접관입니다.", "2025.02.05"),
    Persona("2", "날카로운 면접관", "깊이 있는 기술 질문을 주로 합니다.", "persona2", "당신은 기술 깊이를 묻는 면접관입니다.", "2025.02.03"),
    Persona("3", "비즈니스 관점 면접관", "비즈니스 임팩트와 협업 경험을 묻습니다.", "persona3", "당신은 비즈니스 관점의 면접관입니다.", "2025.02.01")
)

class PersonaRepositoryImpl(
    private val personaApi: PersonaApiService
) : PersonaRepository {

    override suspend fun getPersonas(
        activeOnly: Boolean,
        customOnly: Boolean,
        defaultOnly: Boolean
    ): ApiResult<List<Persona>> = withContext(Dispatchers.IO) {
        ApiResult.Success(mockPersonas.toList())
    }

    override suspend fun createPersona(
        name: String,
        description: String,
        prompt: String,
        isActive: Boolean
    ): ApiResult<Persona> = withContext(Dispatchers.IO) {
        val newId = (mockPersonas.maxOfOrNull { it.id.toIntOrNull() ?: 0 } ?: 0) + 1
        val newPersona = Persona(
            id = newId.toString(),
            title = name.trim(),
            description = description.trim(),
            iconType = "custom",
            prompt = prompt.trim(),
            lastModified = "2025.02.07"
        )
        mockPersonas.add(newPersona)
        ApiResult.Success(newPersona)
    }

    override suspend fun updatePersona(
        id: Int,
        name: String,
        description: String,
        prompt: String,
        isActive: Boolean
    ): ApiResult<Persona> = withContext(Dispatchers.IO) {
        val index = mockPersonas.indexOfFirst { it.id == id.toString() }
        if (index < 0) return@withContext ApiResult.Error(message = "페르소나를 찾을 수 없습니다. (Mock)")
        val updated = mockPersonas[index].copy(
            title = name.trim(),
            description = description.trim(),
            prompt = prompt.trim(),
            lastModified = "2025.02.07"
        )
        mockPersonas[index] = updated
        ApiResult.Success(updated)
    }

    override suspend fun deletePersona(id: Int): ApiResult<Unit> = withContext(Dispatchers.IO) {
        mockPersonas.removeAll { it.id == id.toString() }
        ApiResult.Success(Unit)
    }
}

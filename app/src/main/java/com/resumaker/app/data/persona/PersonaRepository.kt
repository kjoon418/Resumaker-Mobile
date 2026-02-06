package com.resumaker.app.data.persona

import com.resumaker.app.data.remote.ApiResult
import com.resumaker.app.data.remote.dto.CreatePersonaRequest
import com.resumaker.app.data.remote.dto.PersonaDto
import com.resumaker.app.model.Persona
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
// import retrofit2.HttpException
// import java.io.IOException

/** [임시 Mock] 메모리 목 데이터. API 복구 시 제거 */
private val mockPersonas = mutableListOf(
    Persona("1", "친절한 면접관", "많은 피드백을 주며 대화를 이끌어 줍니다.", "persona1", "당신은 친절하고 격려하는 면접관입니다.", "2025.02.05"),
    Persona("2", "날카로운 면접관", "깊이 있는 기술 질문을 주로 합니다.", "persona2", "당신은 기술 깊이를 묻는 면접관입니다.", "2025.02.03"),
    Persona("3", "비즈니스 관점 면접관", "비즈니스 임팩트와 협업 경험을 묻습니다.", "persona3", "당신은 비즈니스 관점의 면접관입니다.", "2025.02.01")
)

/**
 * 페르소나 목록 등 페르소나 관련 데이터 소스 진입점.
 */
class PersonaRepository(
    private val personaApi: PersonaApiService
) {

    /**
     * 페르소나 목록을 조회합니다.
     * [임시] API 호출 대신 Mock 목록 반환.
     */
    suspend fun getPersonas(
        activeOnly: Boolean = false,
        customOnly: Boolean = false,
        defaultOnly: Boolean = false
    ): ApiResult<List<Persona>> = withContext(Dispatchers.IO) {
        // try {
        //     val list = personaApi.getPersonas(activeOnly = activeOnly, customOnly = customOnly, defaultOnly = defaultOnly)
        //     ApiResult.Success(list.map { it.toPersona() })
        // } catch (e: HttpException) { ... } catch (e: IOException) { ApiResult.NetworkError }
        ApiResult.Success(mockPersonas.toList())
    }

    /**
     * 새 페르소나를 생성합니다.
     * [임시] API 대신 Mock 목록에 추가 후 반환.
     */
    suspend fun createPersona(
        name: String,
        description: String,
        prompt: String,
        isActive: Boolean = true
    ): ApiResult<Persona> = withContext(Dispatchers.IO) {
        // try { val request = CreatePersonaRequest(...); val dto = personaApi.createPersona(request); ... }
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

    /**
     * 페르소나를 수정합니다.
     * [임시] API 대신 Mock 목록 내 항목 갱신 후 반환.
     */
    suspend fun updatePersona(
        id: Int,
        name: String,
        description: String,
        prompt: String,
        isActive: Boolean = true
    ): ApiResult<Persona> = withContext(Dispatchers.IO) {
        // try { val request = CreatePersonaRequest(...); val dto = personaApi.updatePersona(id, request); ... }
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

    /**
     * 페르소나를 삭제합니다.
     * [임시] API 대신 Mock 목록에서 제거.
     */
    suspend fun deletePersona(id: Int): ApiResult<Unit> = withContext(Dispatchers.IO) {
        // try { personaApi.deletePersona(id); ApiResult.Success(Unit) } catch (e: HttpException) { ... }
        mockPersonas.removeAll { it.id == id.toString() }
        ApiResult.Success(Unit)
    }
}

// [임시 Mock] API 복구 시 사용
private fun PersonaDto.toPersona(): Persona = Persona(
    id = id.toString(),
    title = name,
    description = description,
    iconType = if (isDefault) "default" else "custom",
    prompt = prompt,
    lastModified = updatedAt ?: createdAt ?: ""
)

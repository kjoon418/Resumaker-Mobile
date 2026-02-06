package com.resumaker.app.data.persona

import com.resumaker.app.data.remote.ApiResult
import com.resumaker.app.data.remote.dto.CreatePersonaRequest
import com.resumaker.app.data.remote.dto.PersonaDto
import com.resumaker.app.model.Persona
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * 페르소나 목록 등 페르소나 관련 데이터 소스 진입점.
 */
class PersonaRepository(
    private val personaApi: PersonaApiService
) {

    /**
     * 페르소나 목록을 조회합니다.
     *
     * @param activeOnly 활성화된 페르소나만 조회 (null이면 미적용)
     * @param customOnly 커스텀 페르소나만 조회 (null이면 미적용)
     * @param defaultOnly 기본 페르소나만 조회 (null이면 미적용)
     * @return [ApiResult.Success] 시 [List]<[Persona]>, 실패 시 [ApiResult.Error] 또는 [ApiResult.NetworkError]
     */
    suspend fun getPersonas(
        activeOnly: Boolean = false,
        customOnly: Boolean = false,
        defaultOnly: Boolean = false
    ): ApiResult<List<Persona>> = withContext(Dispatchers.IO) {
        try {
            val list = personaApi.getPersonas(
                activeOnly = activeOnly,
                customOnly = customOnly,
                defaultOnly = defaultOnly
            )
            ApiResult.Success(list.map { it.toPersona() })
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val message = errorBody?.takeIf { it.isNotBlank() } ?: "페르소나 목록 조회에 실패했습니다. (${e.code()})"
            ApiResult.Error(message = message, code = e.code())
        } catch (e: IOException) {
            ApiResult.NetworkError
        } catch (e: Exception) {
            ApiResult.Error(message = e.message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }

    /**
     * 새 페르소나를 생성합니다.
     * POST /api/personas/ 호출. 사용자 생성이므로 is_default는 서버에서 false로 설정됩니다.
     *
     * @param name 페르소나 이름
     * @param description 상세 설명
     * @param prompt AI에게 전달될 지시문
     * @param isActive 생성 즉시 활성화 여부 (기본 true)
     * @return [ApiResult.Success] 시 생성된 [Persona], 실패 시 [ApiResult.Error] 또는 [ApiResult.NetworkError]
     */
    suspend fun createPersona(
        name: String,
        description: String,
        prompt: String,
        isActive: Boolean = true
    ): ApiResult<Persona> = withContext(Dispatchers.IO) {
        try {
            val request = CreatePersonaRequest(
                name = name.trim(),
                description = description.trim(),
                prompt = prompt.trim(),
                isActive = isActive
            )
            val dto = personaApi.createPersona(request)
            ApiResult.Success(dto.toPersona())
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val message = errorBody?.takeIf { it.isNotBlank() } ?: "페르소나 생성에 실패했습니다. (${e.code()})"
            ApiResult.Error(message = message, code = e.code())
        } catch (e: IOException) {
            ApiResult.NetworkError
        } catch (e: Exception) {
            ApiResult.Error(message = e.message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }
}

private fun PersonaDto.toPersona(): Persona = Persona(
    id = id.toString(),
    title = name,
    description = description,
    iconType = if (isDefault) "default" else "custom",
    lastModified = updatedAt ?: createdAt ?: ""
)

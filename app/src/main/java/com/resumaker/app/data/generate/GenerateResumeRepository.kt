package com.resumaker.app.data.generate

import com.resumaker.app.data.remote.ApiResult
import com.resumaker.app.data.remote.dto.GenerateResumeRequest
import com.resumaker.app.data.remote.dto.GenerateResumeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * 이력서 생성 API 진입점.
 * - [setPendingRequest]: Step2에서 "다음 단계로" 시 폼 데이터 저장. Step3에서 [getAndClearPendingRequest]로 읽어 API 호출.
 * - [setGeneratedResume] / [getGeneratedResume]: Step3 API 성공 시 응답 저장. Step4에서 화면 렌더링용으로 읽음.
 */
class GenerateResumeRepository(
    private val api: GenerateResumeApiService
) {

    private var _pendingRequest: GenerateResumeRequest? = null
    private var _generatedResume: GenerateResumeResponse? = null

    fun setPendingRequest(request: GenerateResumeRequest) {
        _pendingRequest = request
        _generatedResume = null
    }

    fun getAndClearPendingRequest(): GenerateResumeRequest? {
        return _pendingRequest.also { _pendingRequest = null }
    }

    fun setGeneratedResume(response: GenerateResumeResponse) {
        _generatedResume = response
    }

    fun getGeneratedResume(): GenerateResumeResponse? = _generatedResume

    suspend fun generate(request: GenerateResumeRequest): ApiResult<GenerateResumeResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.generate(request)
                ApiResult.Success(response)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val message = errorBody?.takeIf { it.isNotBlank() }
                    ?: "이력서 생성에 실패했습니다. (${e.code()})"
                ApiResult.Error(message = message, code = e.code())
            } catch (e: IOException) {
                ApiResult.NetworkError
            } catch (e: Exception) {
                ApiResult.Error(message = e.message ?: "이력서 생성 중 오류가 발생했습니다.")
            }
        }
}

package com.resumaker.app.data.mypage

import com.resumaker.app.data.remote.ApiResult
import com.resumaker.app.data.remote.dto.mypage.MypageResponse
import com.resumaker.app.data.remote.dto.mypage.MypageUpdateRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * 마이페이지(이력서 정보) 조회·수정 데이터 소스 진입점.
 */
class MypageRepository(
    private val mypageApi: MypageApiService
) {

    /**
     * 마이페이지 정보를 조회합니다.
     * GET /api/resume/mypage 호출.
     *
     * @return [ApiResult.Success] 시 [MypageResponse], 실패 시 [ApiResult.Error] 또는 [ApiResult.NetworkError]
     */
    suspend fun getMypage(): ApiResult<MypageResponse> = withContext(Dispatchers.IO) {
        try {
            val response = mypageApi.getMypage()
            ApiResult.Success(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val message = errorBody?.takeIf { it.isNotBlank() } ?: "마이페이지 조회에 실패했습니다. (${e.code()})"
            ApiResult.Error(message = message, code = e.code())
        } catch (e: IOException) {
            ApiResult.NetworkError
        } catch (e: Exception) {
            ApiResult.Error(message = e.message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }

    /**
     * 마이페이지 정보를 수정합니다.
     * PUT /api/resume/mypage 호출.
     *
     * @param request 수정할 학력/수상/자격증/경력 목록
     * @return [ApiResult.Success] 시 수정 반영된 [MypageResponse], 실패 시 [ApiResult.Error] 또는 [ApiResult.NetworkError]
     */
    suspend fun updateMypage(request: MypageUpdateRequest): ApiResult<MypageResponse> = withContext(Dispatchers.IO) {
        try {
            val response = mypageApi.updateMypage(request)
            ApiResult.Success(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val message = errorBody?.takeIf { it.isNotBlank() } ?: "마이페이지 수정에 실패했습니다. (${e.code()})"
            ApiResult.Error(message = message, code = e.code())
        } catch (e: IOException) {
            ApiResult.NetworkError
        } catch (e: Exception) {
            ApiResult.Error(message = e.message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }
}

package com.resumaker.app.data.remote

/**
 * API 호출 결과를 나타내는 sealed class.
 * Repository 계층에서 네트워크 성공/실패/에러를 일관되게 표현합니다.
 */
sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>()
    object NetworkError : ApiResult<Nothing>()
}

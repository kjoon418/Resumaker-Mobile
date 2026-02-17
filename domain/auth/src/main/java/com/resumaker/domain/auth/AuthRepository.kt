package com.resumaker.domain.auth

import com.resumaker.domain.common.ApiResult
import com.resumaker.domain.common.model.UserProfile

/**
 * 인증 Repository 인터페이스.
 */
interface AuthRepository {
    suspend fun login(email: String, password: String): ApiResult<LoginResult>
    suspend fun logout(): ApiResult<LogoutResult>
    suspend fun register(params: RegisterParams): ApiResult<RegisterResult>
}

data class LoginResult(val message: String, val user: UserProfile)
data class LogoutResult(val message: String)

data class RegisterParams(
    val username: String,
    val email: String,
    val password: String,
    val name: String,
    val age: Int,
    val gender: String,
    val job: String,
    val phoneNumber: String
)

data class RegisterResult(val message: String, val user: UserProfile)

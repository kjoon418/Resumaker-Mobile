package com.resumaker.data.auth

import com.resumaker.domain.common.ApiResult
import com.resumaker.domain.common.model.UserProfile
import com.resumaker.domain.auth.AuthRepository
import com.resumaker.domain.auth.LoginResult
import com.resumaker.domain.auth.LogoutResult
import com.resumaker.domain.auth.RegisterParams
import com.resumaker.domain.auth.RegisterResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 인증 Repository 구현.
 */
class AuthRepositoryImpl(
    private val authApi: AuthApiService
) : AuthRepository {

    override suspend fun login(email: String, password: String): ApiResult<LoginResult> =
        withContext(Dispatchers.IO) {
            val mockUser = UserProfile(
                name = "홍길동",
                email = email.ifBlank { "hong@example.com" },
                age = 28,
                gender = "MALE",
                job = "프론트엔드 개발자",
                phoneNumber = "010-1234-5678"
            )
            ApiResult.Success(LoginResult(message = "로그인 성공 (Mock)", user = mockUser))
        }

    override suspend fun logout(): ApiResult<LogoutResult> = withContext(Dispatchers.IO) {
        ApiResult.Success(LogoutResult(message = "로그아웃되었습니다. (Mock)"))
    }

    override suspend fun register(params: RegisterParams): ApiResult<RegisterResult> =
        withContext(Dispatchers.IO) {
            val mockUser = UserProfile(
                name = params.name,
                email = params.email,
                age = params.age,
                gender = params.gender,
                job = params.job,
                phoneNumber = params.phoneNumber
            )
            ApiResult.Success(RegisterResult(message = "회원가입 완료 (Mock)", user = mockUser))
        }
}

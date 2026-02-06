package com.resumaker.app.data.auth

import com.resumaker.app.data.remote.ApiResult
import com.resumaker.app.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
// [임시 Mock] import com.resumaker.app.data.remote.dto.LoginRequest
// import com.resumaker.app.data.remote.dto.RegisterRequest
// import com.resumaker.app.data.remote.dto.UserDto
// import retrofit2.HttpException
// import java.io.IOException

/**
 * 인증 관련 데이터 소스 진입점.
 * 로그인 성공 시 서버가 설정한 세션 쿠키는 [RetrofitClient]의 OkHttp CookieJar에 저장되며,
 * 이후 API 요청에 자동으로 포함됩니다.
 *
 * @param authApi Koin으로 주입되는 [AuthApiService]
 */
class AuthRepository(
    private val authApi: AuthApiService
) {

    /**
     * 이메일과 비밀번호로 로그인합니다.
     * 서버 API는 username 필드를 사용하므로, 이메일을 username으로 전달합니다.
     *
     * @param email 사용자 이메일
     * @param password 비밀번호
     * @return [ApiResult.Success] 시 [LoginResult] 반환, 실패 시 [ApiResult.Error] 또는 [ApiResult.NetworkError]
     */
    suspend fun login(email: String, password: String): ApiResult<LoginResult> = withContext(Dispatchers.IO) {
        // [임시] API 호출 주석 처리, Mock 데이터로 로그인 성공 처리
        // try {
        //     val request = LoginRequest(email = email.trim(), password = password)
        //     val response = authApi.login(request)
        //     val userProfile = response.user.toUserProfile()
        //     ApiResult.Success(LoginResult(message = response.message, user = userProfile))
        // } catch (e: HttpException) {
        //     val errorBody = e.response()?.errorBody()?.string()
        //     val message = errorBody?.takeIf { it.isNotBlank() } ?: "로그인에 실패했습니다. (${e.code()})"
        //     ApiResult.Error(message = message, code = e.code())
        // } catch (e: IOException) {
        //     ApiResult.NetworkError
        // } catch (e: Exception) {
        //     ApiResult.Error(message = e.message ?: "알 수 없는 오류가 발생했습니다.")
        // }
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

    /**
     * 로그아웃합니다.
     * POST /api/users/logout/ 호출로 서버 세션을 종료하고, 클라이언트 쿠키도 무효화됩니다.
     *
     * @return [ApiResult.Success] 시 [LogoutResult] 반환, 실패 시 [ApiResult.Error] 또는 [ApiResult.NetworkError]
     */
    suspend fun logout(): ApiResult<LogoutResult> = withContext(Dispatchers.IO) {
        // [임시] API 호출 주석 처리, Mock 성공 반환
        // try {
        //     val response = authApi.logout()
        //     ApiResult.Success(LogoutResult(message = response.message))
        // } catch (e: HttpException) { ... } catch (e: IOException) { ... }
        ApiResult.Success(LogoutResult(message = "로그아웃되었습니다. (Mock)"))
    }

    /**
     * 회원가입합니다.
     * POST /api/users/register/ 호출. 비밀번호 8자 이상, 성별 M/F/O.
     *
     * @param params [RegisterParams] (username, email, password, name, age, gender, job, phoneNumber)
     * @return [ApiResult.Success] 시 [RegisterResult] 반환, 실패 시 [ApiResult.Error] 또는 [ApiResult.NetworkError]
     */
    suspend fun register(params: RegisterParams): ApiResult<RegisterResult> = withContext(Dispatchers.IO) {
        // [임시] API 호출 주석 처리, Mock 성공 반환
        // try {
        //     val request = RegisterRequest(...)
        //     val response = authApi.register(request)
        //     ...
        // } catch (e: HttpException) { ... } catch (e: IOException) { ... }
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

/**
 * 로그인 성공 시 반환하는 데이터.
 */
data class LoginResult(
    val message: String,
    val user: UserProfile
)

/**
 * 로그아웃 성공 시 반환하는 데이터.
 */
data class LogoutResult(
    val message: String
)

/**
 * 회원가입 요청 파라미터.
 * 성별은 API 규격에 맞게 M / F / O 로 전달.
 */
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

/**
 * 회원가입 성공 시 반환하는 데이터.
 */
data class RegisterResult(
    val message: String,
    val user: UserProfile
)

// [임시 Mock] API 복구 시 사용
// private fun UserDto.toUserProfile(): UserProfile = UserProfile(
//     name = name, email = email, age = age, gender = gender, job = job, phoneNumber = phoneNumber
// )

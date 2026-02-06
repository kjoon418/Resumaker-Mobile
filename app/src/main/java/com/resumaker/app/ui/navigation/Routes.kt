package com.resumaker.app.ui.navigation

/**
 * 앱 화면 라우트 상수.
 * 새 화면 추가 시 여기에 라우트를 정의하고 NavHost에 composable을 등록하면 됩니다.
 */
object Routes {
    /** 로그인 화면 */
    const val Login = "login"

    /** 회원가입 화면 */
    const val SignUp = "signup"

    /** 로그인 후 메인: 커리어 매니저 */
    const val Home = "home"

    /** 내 이력서 전체 목록 */
    const val AllResumes = "resumes"

    /** 면접관 페르소나 전체 목록 */
    const val AllPersonas = "personas"

    /** 새 이력서 작성 (추후 구현) */
    const val NewResume = "resumes/new"
}

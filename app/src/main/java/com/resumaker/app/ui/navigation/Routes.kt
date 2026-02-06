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

    /** 새 이력서 작성 1단계: 기존 이력서 제출 */
    const val NewResume = "resumes/new"

    /** 새 이력서 작성 2단계: 이력서 정보 입력 */
    const val NewResumeStep2 = "resumes/new/step2"

    /** 새 이력서 작성 3단계: 이력서 생성 중 */
    const val NewResumeStep3 = "resumes/new/step3"
}

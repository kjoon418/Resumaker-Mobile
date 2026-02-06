package com.resumaker.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import com.resumaker.app.data.remote.ApiResult
import com.resumaker.app.data.auth.AuthRepository
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.resumaker.app.model.Persona
import com.resumaker.app.model.Resume
import com.resumaker.app.ui.careermanager.CareerManagerScreen
import com.resumaker.app.ui.careermanager.CareerManagerViewModel
import com.resumaker.app.ui.login.ResumakerLoginScreen
import com.resumaker.app.ui.navigation.Routes
import com.resumaker.app.ui.personamanagement.PersonaManagementScreen
import com.resumaker.app.ui.personamanagement.PersonaManagementViewModel
import com.resumaker.app.ui.resumelist.ResumeListScreen
import com.resumaker.app.ui.resumecreate.ResumeCompletionScreen
import com.resumaker.app.ui.resumecreate.ResumeDetailInputScreen
import com.resumaker.app.ui.resumeedit.ResumeEditScreen
import com.resumaker.app.ui.resumecreate.ResumeGeneratingScreen
import com.resumaker.app.ui.resumecreate.ResumeUploadScreen
import com.resumaker.app.ui.signup.ResumakerSignUpScreen
import com.resumaker.app.ui.interview.InterviewScreen
import com.resumaker.app.ui.mypage.MyPageScreen
import com.resumaker.app.model.Education
import com.resumaker.app.model.Experience
import com.resumaker.app.model.Certification
import com.resumaker.app.model.Award
import com.resumaker.app.model.UserProfile

@Composable
fun ResumakerApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Login,
        modifier = modifier
    ) {
        composable(Routes.Login) {
            ResumakerLoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                },
                onNavigateToSignUp = { navController.navigate(Routes.SignUp) }
            )
        }

        composable(Routes.SignUp) {
            ResumakerSignUpScreen(
                onBackClick = { navController.popBackStack() },
                onLoginClick = { navController.popBackStack() }
            )
        }

        composable(Routes.Home) {
            val careerManagerViewModel: CareerManagerViewModel = koinViewModel()
            val personas by careerManagerViewModel.personas.collectAsState()
            CareerManagerScreen(
                resumes = sampleResumes,
                personas = personas,
                onViewAllResumes = { navController.navigate(Routes.AllResumes) },
                onViewAllPersonas = { navController.navigate(Routes.AllPersonas) },
                onCreateNewResume = { navController.navigate(Routes.NewResume) },
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Routes.Home) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onBackClick = {
                    navController.navigate(Routes.Login) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.MyPage) {
            val authRepository: AuthRepository = koinInject()
            val scope = rememberCoroutineScope()
            MyPageScreen(
                user = sampleUserProfile,
                onBackClick = { navController.popBackStack() },
                onLogout = {
                    scope.launch {
                        when (val result = authRepository.logout()) {
                            is ApiResult.Success -> navController.navigate(Routes.Login) {
                                popUpTo(Routes.Login) { inclusive = true }
                            }
                            is ApiResult.Error -> { /* TODO: 에러 메시지 표시(스낵바 등) */ }
                            is ApiResult.NetworkError -> { /* TODO: 네트워크 오류 표시 */ }
                        }
                    }
                },
                onWithdraw = { /* 회원 탈퇴 추후 구현 */ },
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Routes.Home) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.AllResumes) {
            ResumeListScreen(
                resumeList = sampleResumes,
                onBackClick = { navController.popBackStack() },
                onSearchClick = { },
                onEditClick = { navController.navigate(Routes.ResumeEdit) },
                onPdfDownloadClick = { },
                onRenameClick = { },
                onDeleteClick = { },
                onAddNewClick = { navController.navigate(Routes.NewResume) },
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Routes.Home) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.AllPersonas) {
            PersonaManagementScreen(
                onBackClick = { navController.popBackStack() },
                onSearchClick = { },
                onAddClick = { },
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Routes.Home) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.NewResume) {
            ResumeUploadScreen(
                onBackClick = { navController.popBackStack() },
                onSkip = { navController.navigate(Routes.NewResumeStep2) },
                onNext = { navController.navigate(Routes.NewResumeStep2) }
            )
        }

        composable(Routes.NewResumeStep2) {
            ResumeDetailInputScreen(
                onBackClick = { navController.popBackStack() },
                onNext = { navController.navigate(Routes.NewResumeStep3) },
                onFormatFileSelect = { }
            )
        }

        composable(Routes.NewResumeStep3) {
            ResumeGeneratingScreen(
                onBackClick = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Home) { inclusive = true }
                    }
                },
                onCompleteClick = { navController.navigate(Routes.NewResumeStep4) }
            )
        }

        composable(Routes.ResumeEdit) {
            ResumeEditScreen(
                onBackClick = { navController.popBackStack() },
                onSaveClick = { },
                onPdfExportClick = { },
                onInterviewClick = { navController.navigate(Routes.Interview) }
            )
        }

        composable(Routes.Interview) {
            InterviewScreen(
                onBackClick = { navController.popBackStack() },
                onExit = { navController.popBackStack() }
            )
        }

        composable(Routes.NewResumeStep4) {
            ResumeCompletionScreen(
                onBackClick = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Home) { inclusive = true }
                    }
                },
                onEditClick = { navController.navigate(Routes.ResumeEdit) },
                onSaveClick = { },
                onCloseClick = { navController.navigate(Routes.Home) { popUpTo(Routes.Home) { inclusive = true } } }
            )
        }
    }
}

private val sampleResumes = listOf(
    Resume("1", "프론트엔드 개발자 이력서", "2025.02.05", "doc"),
    Resume("2", "백엔드 개발자 지원용", "2025.02.01", "doc"),
    Resume("3", "풀스택 포트폴리오", "2025.01.28", "doc")
)

private val samplePersonas = listOf(
    Persona("1", "친절한 면접관", "많은 피드백을 주며 대화를 이끌어 줍니다.", "persona1", "", "2025.02.05"),
    Persona("2", "날카로운 면접관", "깊이 있는 기술 질문을 주로 합니다.", "persona2", "", "2025.02.03"),
    Persona("3", "비즈니스 관점 면접관", "비즈니스 임팩트와 협업 경험을 묻습니다.", "persona3", "", "2025.02.01")
)

private val sampleUserProfile = UserProfile(
    name = "홍길동",
    email = "hong@example.com",
    age = 28,
    gender = "MALE",
    job = "프론트엔드 개발자",
    phoneNumber = "010-1234-5678"
)

private val sampleEducations = listOf(
    Education("서울대학교", "컴퓨터공학과", "2014.03 ~ 2018.02", "4.2/4.5")
)

private val sampleExperiences = listOf(
    Experience(
        company = "(주)테크컴퍼니",
        role = "시니어 프론트엔드 개발자",
        period = "2022.01 ~ 현재",
        description = "React 기반 웹 서비스 개발 및 팀 리드. 성능 최적화 및 디자인 시스템 구축.",
        isCurrent = true
    ),
    Experience(
        company = "이전 회사",
        role = "주니어 개발자",
        period = "2018.03 ~ 2021.12",
        description = "웹 프론트엔드 개발 및 유지보수.",
        isCurrent = false
    )
)

private val sampleCertifications = listOf(
    Certification("정보처리기사", "한국산업인력공단", "2020.05", "1차 합격"),
    Certification("TOEIC", "ETS", "2019.03", "920")
)

private val sampleAwards = listOf(
    Award("우수 사원상", "연간 성과 평가 우수", "2023", "테크컴퍼니")
)

@Composable
private fun PlaceholderScreen(
    title: String,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        IconButton(onClick = onNavigateBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "이 화면은 추후 구현 예정입니다.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

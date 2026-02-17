package com.resumaker.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.resumaker.core.common.model.Resume
import com.resumaker.feature.login.screen.ResumakerLoginScreen
import com.resumaker.feature.login.viewmodel.LogoutEffect
import com.resumaker.feature.login.viewmodel.LogoutViewModel
import com.resumaker.feature.careermanager.screen.CareerManagerScreen
import com.resumaker.feature.careermanager.viewmodel.CareerManagerViewModel
import com.resumaker.core.navigation.NavRoute
import com.resumaker.core.navigation.navigateTo
import com.resumaker.core.navigation.toRouteString
import com.resumaker.feature.careermanager.screen.PersonaManagementScreen
import com.resumaker.feature.careermanager.screen.ResumeListScreen
import com.resumaker.feature.resumebuilder.screen.ResumeCompletionScreen
import com.resumaker.feature.resumebuilder.screen.ResumeDetailInputScreen
import com.resumaker.feature.resumebuilder.screen.ResumeEditScreen
import com.resumaker.feature.resumebuilder.screen.ResumeGeneratingScreen
import com.resumaker.feature.resumebuilder.screen.ResumeUploadScreen
import com.resumaker.feature.signup.screen.ResumakerSignUpScreen
import com.resumaker.feature.interview.screen.InterviewScreen
import com.resumaker.feature.mypage.screen.MyPageScreen
import com.resumaker.domain.common.model.UserProfile
import org.koin.androidx.compose.koinViewModel

@Composable
fun ResumakerApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Login.toRouteString(),
        modifier = modifier
    ) {
        composable(NavRoute.Login.toRouteString()) {
            ResumakerLoginScreen(
                onLoginSuccess = {
                    navController.navigateTo(NavRoute.Home) {
                        popUpTo(NavRoute.Login.toRouteString()) { inclusive = true }
                    }
                },
                onNavigateToSignUp = { navController.navigateTo(NavRoute.SignUp) }
            )
        }

        composable(NavRoute.SignUp.toRouteString()) {
            ResumakerSignUpScreen(
                onBackClick = { navController.popBackStack() },
                onLoginClick = { navController.popBackStack() }
            )
        }

        composable(NavRoute.Home.toRouteString()) {
            val careerManagerViewModel: CareerManagerViewModel = koinViewModel()
            val state by careerManagerViewModel.state.collectAsState()
            CareerManagerScreen(
                resumes = sampleResumes,
                personas = state.personas,
                onViewAllResumes = { navController.navigateTo(NavRoute.AllResumes) },
                onViewAllPersonas = { navController.navigateTo(NavRoute.AllPersonas) },
                onCreateNewResume = { navController.navigateTo(NavRoute.NewResume) },
                onNavigate = { route ->
                    navController.navigateTo(route) {
                        popUpTo(NavRoute.Home.toRouteString()) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onBackClick = {
                    navController.navigateTo(NavRoute.Login) {
                        popUpTo(NavRoute.Login.toRouteString()) { inclusive = true }
                    }
                }
            )
        }

        composable(NavRoute.MyPage.toRouteString()) {
            val logoutViewModel: LogoutViewModel = koinViewModel()
            LaunchedEffect(Unit) {
                logoutViewModel.effect.collect { effect ->
                    when (effect) {
                        is LogoutEffect.NavigateToLogin -> navController.navigateTo(NavRoute.Login) {
                            popUpTo(NavRoute.Login.toRouteString()) { inclusive = true }
                        }
                        is LogoutEffect.ShowError -> { /* TODO: 스낵바 등 에러 표시 */ }
                    }
                }
            }
            MyPageScreen(
                user = sampleUserProfile,
                onBackClick = { navController.popBackStack() },
                onLogout = { logoutViewModel.logout() },
                onWithdraw = { /* 회원 탈퇴 추후 구현 */ },
                onNavigate = { route ->
                    navController.navigateTo(route) {
                        popUpTo(NavRoute.Home.toRouteString()) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(NavRoute.AllResumes.toRouteString()) {
            ResumeListScreen(
                resumeList = sampleResumes,
                onBackClick = { navController.popBackStack() },
                onSearchClick = { },
                onEditClick = { navController.navigateTo(NavRoute.ResumeEdit) },
                onPdfDownloadClick = { },
                onRenameClick = { },
                onDeleteClick = { },
                onAddNewClick = { navController.navigateTo(NavRoute.NewResume) },
                onNavigate = { route ->
                    navController.navigateTo(route) {
                        popUpTo(NavRoute.Home.toRouteString()) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(NavRoute.AllPersonas.toRouteString()) {
            PersonaManagementScreen(
                onBackClick = { navController.popBackStack() },
                onSearchClick = { },
                onAddClick = { },
                onNavigate = { route ->
                    navController.navigateTo(route) {
                        popUpTo(NavRoute.Home.toRouteString()) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(NavRoute.NewResume.toRouteString()) {
            ResumeUploadScreen(
                onBackClick = { navController.popBackStack() },
                onSkip = { navController.navigateTo(NavRoute.NewResumeStep2) },
                onNext = { navController.navigateTo(NavRoute.NewResumeStep2) }
            )
        }

        composable(NavRoute.NewResumeStep2.toRouteString()) {
            ResumeDetailInputScreen(
                onBackClick = { navController.popBackStack() },
                onNext = { navController.navigateTo(NavRoute.NewResumeStep3) },
                onFormatFileSelect = { }
            )
        }

        composable(NavRoute.NewResumeStep3.toRouteString()) {
            ResumeGeneratingScreen(
                onBackClick = {
                    navController.navigateTo(NavRoute.Home) {
                        popUpTo(NavRoute.Home.toRouteString()) { inclusive = true }
                    }
                },
                onCompleteClick = {
                    navController.navigateTo(NavRoute.NewResumeStep4) {
                        popUpTo(NavRoute.NewResumeStep3.toRouteString()) { inclusive = true }
                    }
                }
            )
        }

        composable(NavRoute.ResumeEdit.toRouteString()) {
            ResumeEditScreen(
                onBackClick = { navController.popBackStack() },
                onSaveClick = { },
                onPdfExportClick = { },
                onInterviewClick = { navController.navigateTo(NavRoute.Interview) }
            )
        }

        composable(NavRoute.Interview.toRouteString()) {
            InterviewScreen(
                onBackClick = { navController.popBackStack() },
                onExit = { navController.popBackStack() }
            )
        }

        composable(NavRoute.NewResumeStep4.toRouteString()) {
            ResumeCompletionScreen(
                onBackClick = {
                    navController.navigateTo(NavRoute.Home) {
                        popUpTo(NavRoute.Home.toRouteString()) { inclusive = true }
                    }
                },
                onEditClick = { navController.navigateTo(NavRoute.ResumeEdit) },
                onSaveClick = { },
                onCloseClick = {
                    navController.navigateTo(NavRoute.Home) {
                        popUpTo(NavRoute.Home.toRouteString()) { inclusive = true }
                    }
                }
            )
        }
    }
}

private val sampleResumes = listOf(
    Resume("1", "프론트엔드 개발자 이력서", "2025.02.05", "doc"),
    Resume("2", "백엔드 개발자 지원용", "2025.02.01", "doc"),
    Resume("3", "풀스택 포트폴리오", "2025.01.28", "doc")
)

private val sampleUserProfile = UserProfile(
    name = "홍길동",
    email = "hong@example.com",
    age = 28,
    gender = "MALE",
    job = "프론트엔드 개발자",
    phoneNumber = "010-1234-5678"
)

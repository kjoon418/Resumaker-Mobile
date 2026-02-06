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
import androidx.compose.ui.Alignment
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
import com.resumaker.app.ui.login.ResumakerLoginScreen
import com.resumaker.app.ui.navigation.Routes
import com.resumaker.app.ui.personamanagement.PersonaManagementScreen
import com.resumaker.app.ui.resumelist.ResumeListScreen
import com.resumaker.app.ui.signup.ResumakerSignUpScreen

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
            CareerManagerScreen(
                resumes = sampleResumes,
                personas = samplePersonas,
                onViewAllResumes = { navController.navigate(Routes.AllResumes) },
                onViewAllPersonas = { navController.navigate(Routes.AllPersonas) },
                onCreateNewResume = { navController.navigate(Routes.NewResume) }
            )
        }

        composable(Routes.AllResumes) {
            ResumeListScreen(
                resumeList = sampleResumes,
                onBackClick = { navController.popBackStack() },
                onSearchClick = { },
                onEditClick = { navController.navigate(Routes.NewResume) },
                onPdfDownloadClick = { },
                onRenameClick = { },
                onDeleteClick = { },
                onAddNewClick = { navController.navigate(Routes.NewResume) }
            )
        }

        composable(Routes.AllPersonas) {
            PersonaManagementScreen(
                personaList = samplePersonas,
                onBackClick = { navController.popBackStack() },
                onSearchClick = { },
                onAddClick = { },
                onEditClick = { },
                onDeleteClick = { }
            )
        }

        composable(Routes.NewResume) {
            PlaceholderScreen(
                title = "새 이력서 작성",
                onNavigateBack = { navController.popBackStack() }
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
    Persona("1", "친절한 면접관", "많은 피드백을 주며 대화를 이끌어 줍니다.", "persona1", "2025.02.05"),
    Persona("2", "날카로운 면접관", "깊이 있는 기술 질문을 주로 합니다.", "persona2", "2025.02.03"),
    Persona("3", "비즈니스 관점 면접관", "비즈니스 임팩트와 협업 경험을 묻습니다.", "persona3", "2025.02.01")
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

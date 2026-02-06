package com.resumaker.app.ui.careermanager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.resumaker.app.component.appbar.ResumakerTopBar
import com.resumaker.app.component.bottombar.ResumakerBottomBar
import com.resumaker.app.component.button.PrimaryButton
import com.resumaker.app.component.card.PersonaCard
import com.resumaker.app.component.card.ResumeCard
import com.resumaker.app.component.section.SectionHeader
import com.resumaker.app.model.Persona
import com.resumaker.app.model.Resume
import com.resumaker.app.ui.navigation.Routes
import com.resumaker.app.ui.theme.ResumakerTheme

@Composable
fun CareerManagerScreen(
    resumes: List<Resume>,
    personas: List<Persona>,
    onViewAllResumes: () -> Unit,
    onViewAllPersonas: () -> Unit,
    onCreateNewResume: () -> Unit,
    onMyPageClick: () -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            ResumakerTopBar(
                showBackButton = false,
                onBackClick = { }
            )
        },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                PrimaryButton(
                    text = "+ 새 이력서 작성하기",
                    onClick = onCreateNewResume,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                    showShadow = true
                )
                ResumakerBottomBar(
                    currentRoute = Routes.Home,
                    onNavigate = onNavigate
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = 20.dp,
                    end = 20.dp
                )
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            SectionHeader(title = "내 이력서", onSeeAllClick = onViewAllResumes)
            Spacer(modifier = Modifier.height(16.dp))
            resumes.take(3).forEach { resume ->
                ResumeCard(resume = resume)
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            SectionHeader(
                title = "면접관 페르소나",
                subtitle = "첨삭 및 모의 면접에 활용되는 성향입니다.",
                onSeeAllClick = onViewAllPersonas
            )
            Spacer(modifier = Modifier.height(16.dp))
            personas.take(3).forEach { persona ->
                PersonaCard(persona = persona)
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(200.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CareerManagerScreenPreview() {
    ResumakerTheme {
        CareerManagerScreen(
            resumes = listOf(
                Resume("1", "프론트엔드 개발자 이력서", "2025.02.05", "doc"),
                Resume("2", "백엔드 개발자 지원용", "2025.02.01", "doc"),
                Resume("3", "풀스택 포트폴리오", "2025.01.28", "doc")
            ),
            personas = listOf(
                Persona("1", "친절한 면접관", "많은 피드백을 주며 대화를 이끌어 줍니다.", "persona1"),
                Persona("2", "날카로운 면접관", "깊이 있는 기술 질문을 주로 합니다.", "persona2"),
                Persona("3", "비즈니스 관점 면접관", "비즈니스 임팩트와 협업 경험을 묻습니다.", "persona3")
            ),
            onViewAllResumes = { },
            onViewAllPersonas = { },
            onCreateNewResume = { },
            onMyPageClick = { }
        )
    }
}

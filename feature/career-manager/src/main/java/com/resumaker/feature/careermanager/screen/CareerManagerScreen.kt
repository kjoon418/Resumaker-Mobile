package com.resumaker.feature.careermanager.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.resumaker.core.ui.appbar.ResumakerTopBar
import com.resumaker.core.ui.bottombar.ResumakerBottomBar
import com.resumaker.core.ui.button.PrimaryButton
import com.resumaker.core.ui.card.PersonaCard
import com.resumaker.core.ui.card.ResumeCard
import com.resumaker.feature.careermanager.component.section.SectionHeader
import com.resumaker.domain.persona.model.Persona
import com.resumaker.core.common.model.Resume
import com.resumaker.core.designsystem.DesignSystem
import com.resumaker.core.designsystem.IconType
import com.resumaker.core.navigation.NavRoute
import com.resumaker.feature.careermanager.preview.CareerManagerPreviewData
import com.resumaker.feature.careermanager.preview.CareerManagerPreviewProvider

@Composable
fun CareerManagerScreen(
    resumes: List<Resume>,
    personas: List<Persona>,
    onViewAllResumes: () -> Unit,
    onViewAllPersonas: () -> Unit,
    onCreateNewResume: () -> Unit,
    onNavigate: (NavRoute) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    BackHandler(onBack = onBackClick)
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
                    currentRoute = NavRoute.Home,
                    onNavigate = onNavigate
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DesignSystem.colors.ScreenBackground)
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
                ResumeCard(
                    resume = resume,
                    icon = {
                        Image(
                            painter = painterResource(resume.iconType.drawableResId),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                )
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
                PersonaCard(
                    persona = persona,
                    icon = {
                        Image(
                            painter = painterResource(IconType.fromString(persona.iconType).drawableResId),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(200.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CareerManagerScreenPreview(
    @PreviewParameter(CareerManagerPreviewProvider::class) data: CareerManagerPreviewData
) {
    MaterialTheme {
        CareerManagerScreen(
            resumes = data.resumes,
            personas = data.personas,
            onViewAllResumes = { },
            onViewAllPersonas = { },
            onCreateNewResume = { }
        )
    }
}

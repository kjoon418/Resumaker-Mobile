package com.resumaker.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.resumaker.feature.mypage.component.appbar.MyPageTopBar
import com.resumaker.core.ui.bottombar.ResumakerBottomBar
import com.resumaker.core.ui.button.PrimaryButton
import com.resumaker.feature.mypage.component.card.InfoSectionCard
import com.resumaker.feature.mypage.component.section.AccountManagementSection
import com.resumaker.feature.mypage.component.section.ProfileHeaderSection
import com.resumaker.feature.mypage.component.item.AwardItem
import com.resumaker.feature.mypage.component.item.CertificationItem
import com.resumaker.feature.mypage.component.item.EducationItem
import com.resumaker.feature.mypage.component.BasicInfoGrid
import com.resumaker.feature.mypage.component.item.ExperienceItem
import com.resumaker.domain.common.model.Award
import com.resumaker.domain.common.model.Certification
import com.resumaker.domain.common.model.Education
import com.resumaker.domain.common.model.Experience
import com.resumaker.domain.common.model.UserProfile
import com.resumaker.core.designsystem.DesignSystem
import com.resumaker.core.navigation.NavRoute
import com.resumaker.feature.mypage.contract.MyPageIntent
import com.resumaker.feature.mypage.preview.MyPagePreviewData
import com.resumaker.feature.mypage.preview.MyPagePreviewProvider
import com.resumaker.feature.mypage.viewmodel.MyPageViewModel
import androidx.compose.material3.MaterialTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyPageScreen(
    user: UserProfile,
    onBackClick: () -> Unit,
    onEditBasicInfo: () -> Unit = {},
    onAddEducation: () -> Unit = {},
    onAddExperience: () -> Unit = {},
    onAddCertification: () -> Unit = {},
    onAddAward: () -> Unit = {},
    onLogout: () -> Unit = {},
    onWithdraw: () -> Unit = {},
    onNavigate: (NavRoute) -> Unit = {},
    viewModel: MyPageViewModel = koinViewModel()
) {
    val colors = DesignSystem.colors
    val state by viewModel.state.collectAsState()
    val educations = state.educations
    val experiences = state.experiences
    val certifications = state.certifications
    val awards = state.awards

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.onIntent(MyPageIntent.ClearErrorMessage)
        }
    }

    Scaffold(
        topBar = {
            MyPageTopBar(onBackClick = onBackClick)
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.ScreenBackground)
            ) {
                if (state.hasChanges) {
                    PrimaryButton(
                        text = if (state.isSaving) "저장 중…" else "저장",
                        onClick = { viewModel.onIntent(MyPageIntent.SaveMypage) },
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                        enabled = !state.isSaving
                    )
                }
                ResumakerBottomBar(
                    currentRoute = NavRoute.MyPage,
                    onNavigate = onNavigate
                )
            }
        },
        containerColor = colors.ScreenBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colors.ScreenBackground)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(20.dp)
                    )
                }
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                        ) {
                            ProfileHeaderSection(name = user.name, job = user.job)

                            Spacer(modifier = Modifier.height(16.dp))

                            InfoSectionCard(
                                title = "기본 정보",
                                onEditClick = onEditBasicInfo,
                                content = { BasicInfoGrid(user = user) }
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            InfoSectionCard(
                                title = "학력 사항",
                                onAddClick = onAddEducation,
                                content = {
                                    educations.forEach { edu ->
                                        EducationItem(edu)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            InfoSectionCard(
                                title = "경력 사항",
                                onAddClick = onAddExperience,
                                content = {
                                    experiences.forEach { exp ->
                                        ExperienceItem(exp)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            InfoSectionCard(
                                title = "자격증",
                                onAddClick = onAddCertification,
                                content = {
                                    certifications.forEach { cert ->
                                        CertificationItem(cert)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            InfoSectionCard(
                                title = "수상 경력",
                                onAddClick = onAddAward,
                                content = {
                                    awards.forEach { award ->
                                        AwardItem(award)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(24.dp))

                            AccountManagementSection(
                                onLogout = onLogout,
                                onWithdraw = onWithdraw
                            )

                            Spacer(modifier = Modifier.height(40.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MyPageScreenPreviewContent(
    user: UserProfile,
    educations: List<Education>,
    experiences: List<Experience>,
    certifications: List<Certification>,
    awards: List<Award>,
    hasChanges: Boolean = false
) {
    val colors = DesignSystem.colors
    Scaffold(
        topBar = { MyPageTopBar(onBackClick = { }) },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth().background(colors.ScreenBackground)) {
                if (hasChanges) {
                    PrimaryButton(
                        text = "저장",
                        onClick = { },
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                    )
                }
                ResumakerBottomBar(currentRoute = NavRoute.MyPage, onNavigate = { })
            }
        },
        containerColor = colors.ScreenBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colors.ScreenBackground)
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                ProfileHeaderSection(name = user.name, job = user.job)
                Spacer(modifier = Modifier.height(16.dp))
                InfoSectionCard(title = "기본 정보", onEditClick = { }, content = { BasicInfoGrid(user = user) })
                Spacer(modifier = Modifier.height(16.dp))
                InfoSectionCard(
                    title = "학력 사항",
                    onAddClick = { },
                    content = { educations.forEach { EducationItem(it) } }
                )
                Spacer(modifier = Modifier.height(16.dp))
                InfoSectionCard(
                    title = "경력 사항",
                    onAddClick = { },
                    content = { experiences.forEach { ExperienceItem(it) } }
                )
                Spacer(modifier = Modifier.height(16.dp))
                InfoSectionCard(
                    title = "자격증",
                    onAddClick = { },
                    content = { certifications.forEach { CertificationItem(it) } }
                )
                Spacer(modifier = Modifier.height(16.dp))
                InfoSectionCard(
                    title = "수상 경력",
                    onAddClick = { },
                    content = { awards.forEach { AwardItem(it) } }
                )
                Spacer(modifier = Modifier.height(24.dp))
                AccountManagementSection(onLogout = { }, onWithdraw = { })
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview(
    @PreviewParameter(MyPagePreviewProvider::class) data: MyPagePreviewData
) {
    MaterialTheme {
        MyPageScreenPreviewContent(
            user = data.user,
            educations = data.educations,
            experiences = data.experiences,
            certifications = data.certifications,
            awards = data.awards,
            hasChanges = data.hasChanges
        )
    }
}

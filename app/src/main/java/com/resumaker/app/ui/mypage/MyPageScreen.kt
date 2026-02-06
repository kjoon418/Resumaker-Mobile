package com.resumaker.app.ui.mypage

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
import androidx.compose.ui.unit.dp
import com.resumaker.app.component.appbar.MyPageTopBar
import com.resumaker.app.component.bottombar.ResumakerBottomBar
import com.resumaker.app.component.button.PrimaryButton
import com.resumaker.app.component.card.InfoSectionCard
import com.resumaker.app.component.section.AccountManagementSection
import com.resumaker.app.component.section.ProfileHeaderSection
import com.resumaker.app.component.item.AwardItem
import com.resumaker.app.component.item.CertificationItem
import com.resumaker.app.component.item.EducationItem
import com.resumaker.app.component.item.ExperienceItem
import com.resumaker.app.model.Award
import com.resumaker.app.model.Certification
import com.resumaker.app.model.Education
import com.resumaker.app.model.Experience
import com.resumaker.app.model.UserProfile
import com.resumaker.app.ui.navigation.Routes
import com.resumaker.app.ui.theme.ResumakerTheme
import org.koin.androidx.compose.koinViewModel

private val ScreenBackground = Color(0xFFF8FAFC)

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
    onNavigate: (String) -> Unit = {},
    viewModel: MyPageViewModel = koinViewModel()
) {
    val mypageData by viewModel.mypageData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isSaving by viewModel.isSaving.collectAsState()
    val hasChanges by viewModel.hasChanges.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val educations = viewModel.educationsForUi(mypageData)
    val experiences = viewModel.experiencesForUi(mypageData)
    val certifications = viewModel.certificationsForUi(mypageData)
    val awards = viewModel.awardsForUi(mypageData)

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(errorMessage) {
        errorMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearErrorMessage()
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
                    .background(ScreenBackground)
            ) {
                if (hasChanges) {
                    PrimaryButton(
                        text = if (isSaving) "저장 중…" else "저장",
                        onClick = { viewModel.saveMypage() },
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                        enabled = !isSaving
                    )
                }
                ResumakerBottomBar(
                    currentRoute = Routes.MyPage,
                    onNavigate = onNavigate
                )
            }
        },
        containerColor = ScreenBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(ScreenBackground)
        ) {
            when {
                isLoading -> {
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

private val sampleUser = UserProfile(
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

/** Preview 전용: ViewModel 없이 샘플 데이터로 화면만 렌더링 */
@Composable
private fun MyPageScreenPreviewContent(
    user: UserProfile,
    educations: List<Education>,
    experiences: List<Experience>,
    certifications: List<Certification>,
    awards: List<Award>,
    hasChanges: Boolean = false
) {
    Scaffold(
        topBar = { MyPageTopBar(onBackClick = { }) },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth().background(ScreenBackground)) {
                if (hasChanges) {
                    PrimaryButton(
                        text = "저장",
                        onClick = { },
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                    )
                }
                ResumakerBottomBar(currentRoute = Routes.MyPage, onNavigate = { })
            }
        },
        containerColor = ScreenBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(ScreenBackground)
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
private fun MyPageScreenPreview() {
    ResumakerTheme {
        MyPageScreenPreviewContent(
            user = sampleUser,
            educations = sampleEducations,
            experiences = sampleExperiences,
            certifications = sampleCertifications,
            awards = sampleAwards
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenWithChangesPreview() {
    ResumakerTheme {
        MyPageScreenPreviewContent(
            user = sampleUser,
            educations = sampleEducations,
            experiences = sampleExperiences,
            certifications = sampleCertifications,
            awards = sampleAwards,
            hasChanges = true
        )
    }
}

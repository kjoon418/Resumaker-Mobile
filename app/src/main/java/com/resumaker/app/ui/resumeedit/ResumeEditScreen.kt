package com.resumaker.app.ui.resumeedit

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.component.button.PrimaryButton
import com.resumaker.app.component.card.FeedbackOptionCard
import com.resumaker.app.component.card.GeneratedResumeContentCard
import com.resumaker.app.component.card.PlaceholderResumeCard
import com.resumaker.app.component.card.SelectionItemCard
import com.resumaker.app.component.input.PrimaryTextField
import com.resumaker.app.ui.theme.ResumakerTheme
import org.koin.androidx.compose.koinViewModel

private val PrimaryBlue = Color(0xFF2161EE)
private val FabColor = Color(0xFF6366F1)
private val PrimaryDark = Color(0xFF0F172A)

private data class InitialEditState(
    val name: String,
    val career: String,
    val strengths: String,
    val projects: String
)

private const val PREFS_NAME = "resumaker_prefs"
private const val KEY_VISITED_RESUME_EDIT = "has_visited_resume_edit"

/** AI 버튼 쪽(오른쪽)으로 꼬리가 나오는 말풍선 모양 */
private class SpeechBubbleTailRightShape(
    private val cornerRadius: Dp = 12.dp,
    private val tailWidth: Dp = 10.dp,
    private val tailBaseHeight: Dp = 12.dp
) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val r = with(density) { cornerRadius.toPx() }
        val tw = with(density) { tailWidth.toPx() }
        val th = with(density) { tailBaseHeight.toPx() }
        val bodyRight = size.width - tw
        val path = Path().apply {
            moveTo(r, 0f)
            lineTo(bodyRight - r, 0f)
            arcTo(Rect(bodyRight - 2 * r, 0f, bodyRight, 2 * r), 270f, 90f, false)
            lineTo(bodyRight, size.height / 2 - th / 2)
            lineTo(size.width, size.height / 2)
            lineTo(bodyRight, size.height / 2 + th / 2)
            lineTo(bodyRight, size.height - r)
            arcTo(Rect(bodyRight - 2 * r, size.height - 2 * r, bodyRight, size.height), 0f, 90f, false)
            lineTo(r, size.height)
            arcTo(Rect(0f, size.height - 2 * r, 2 * r, size.height), 90f, 90f, false)
            lineTo(0f, r)
            arcTo(Rect(0f, 0f, 2 * r, 2 * r), 180f, 90f, false)
            close()
        }
        return Outline.Generic(path)
    }
}

// TODO: 서버 API 연결 시 ViewModel에서 관리
private data class AntiPatternItem(val title: String, val description: String)

private val antiPatterns = listOf(
    AntiPatternItem("단순 나열형 이력서", "경험과 기술을 단순 나열만 하고 있는지 확인합니다."),
    AntiPatternItem("근거 없는 이력서", "주장에 대한 구체적 근거가 부족한지 확인합니다."),
    AntiPatternItem("결과만 서술한 이력서", "과정 없이 결과만 나열하고 있는지 확인합니다."),
    AntiPatternItem("가독성 박살", "구조와 가독성에 문제가 있는지 확인합니다.")
)

/** [임시 Mock] 안티패턴별 AI 피드백 텍스트 */
private val mockAntiPatternFeedback = mapOf(
    "단순 나열형 이력서" to "현재 이력서에서 'React, TypeScript 사용'과 같이 기술 스택을 나열만 하고 있습니다. 각 기술을 어떤 프로젝트에서 어떻게 활용했는지 한 줄이라도 구체적으로 적어 주시면 좋습니다. 예: 'React로 대시보드 컴포넌트를 설계하고, 재사용 가능한 훅으로 상태 로직을 분리했습니다.'",
    "근거 없는 이력서" to "‘성능을 개선했다’는 표현에 비해 구체적인 수치나 방법이 없습니다. 개선 전·후 지표(예: LCP 2.1s → 1.2s)나 적용한 기법(코드 스플리팅, 메모이제이션 등)을 한두 문장이라도 추가해 주세요.",
    "결과만 서술한 이력서" to "프로젝트 설명이 결과 위주로 되어 있습니다. 과제 인식 → 접근 방법 → 본인의 역할 → 결과 순으로 한 문단씩 정리하면 설득력이 높아집니다.",
    "가독성 박살" to "섹션 구분은 되어 있으나, 한 블록 안에 정보가 많이 몰려 있습니다. '경력 요약', '주요 성과', '기술 스택'처럼 소제목을 나누거나 불릿을 활용해 스캔하기 쉽게 정리해 보세요."
)

/** [임시 Mock] 페르소나별 AI 피드백 텍스트 */
private val mockPersonaFeedback = mapOf(
    "친절한 면접관" to "전반적으로 잘 정리되어 있어서 좋습니다. 다만 '협업 스타일'을 한 문장만이 아니라, 실제로 디자이너·백엔드와 어떻게 소통했는지 구체적인 에피소드를 하나 넣어 주시면 면접에서 이야기하기 편할 것 같아요.",
    "날카로운 면접관" to "기술 스택은 명확한데, '성능 최적화'에서 어떤 메트릭을 개선했는지, 어떤 도구로 측정했는지가 없습니다. 기술 면접에서 바로 질문받을 수 있는 부분이니 한두 줄이라도 보강하는 것을 추천드립니다.",
    "비즈니스 관점 면접관" to "개인 역량은 잘 드러나 있습니다. 다만 이력서만 봤을 때 '비즈니스 임팩트'가 조금 약합니다. 예를 들어 '리디자인으로 대시보드 이탈률 15% 감소'처럼 수치나 효과를 한 군데만 넣어도 좋겠습니다."
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumeEditScreen(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit = {},
    onPdfExportClick: () -> Unit = {},
    onInterviewClick: () -> Unit = {},
    viewModel: ResumeEditViewModel = koinViewModel()
) {
    val personas by viewModel.personas.collectAsState()
    val isLoadingPersonas by viewModel.isLoadingPersonas.collectAsState()
    val personasError by viewModel.personasError.collectAsState()
    val generatedResume by viewModel.generatedResume.collectAsState()
    var isPreviewMode by remember { mutableStateOf(false) }
    var showAiSheet by remember { mutableStateOf(false) }
    var showAntiPatternSheet by remember { mutableStateOf(false) }
    var showPersonaSheet by remember { mutableStateOf(false) }
    var showAiBubble by remember { mutableStateOf(true) }
    var feedbackResultTitle by remember { mutableStateOf("") }
    var feedbackResultText by remember { mutableStateOf("") }

    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
    LaunchedEffect(Unit) {
        showAiBubble = !prefs.getBoolean(KEY_VISITED_RESUME_EDIT, false)
    }
    fun dismissBubble() {
        showAiBubble = false
        prefs.edit().putBoolean(KEY_VISITED_RESUME_EDIT, true).apply()
    }

    val items = generatedResume?.items.orEmpty()
    val (initialName, initialCareer, initialStrengths, initialProjects) = remember(items) {
        if (items.isEmpty()) {
            InitialEditState(
                name = "김민준",
                career = "테크리드 솔루션즈 시니어 UI 디자이너 (2022~)\n• B2B SaaS 대시보드 리디자인 리드\n• 디자인 시스템 구축 및 컴포넌트 문서화",
                strengths = "React · TypeScript · 성능 최적화 · 디자인 시스템",
                projects = "B2B SaaS 대시보드 리디자인 – UI/UX 개선, 컴포넌트 라이브러리 구축"
            )
        } else {
            val position = items.find { it.subTitle == "지원 포지션" }?.content?.takeIf { it.isNotBlank() }.orEmpty()
            val careerVal = items.find { it.subTitle == "한줄 소개" }?.content?.takeIf { it.isNotBlank() }.orEmpty()
            val strengthsVal = items.find { it.subTitle == "핵심 역량" }?.content?.takeIf { it.isNotBlank() }.orEmpty()
            val projectsVal = items.find { it.subTitle == "프로젝트" }?.content?.takeIf { it.isNotBlank() }.orEmpty()
            InitialEditState(
                name = if (position.isNotBlank()) position else "이력서 제목",
                career = if (careerVal.isNotBlank()) careerVal else "경력 요약을 입력하세요.",
                strengths = if (strengthsVal.isNotBlank()) strengthsVal else "핵심 역량을 입력하세요.",
                projects = if (projectsVal.isNotBlank()) projectsVal else "주요 프로젝트를 입력하세요."
            )
        }
    }
    var name by remember(initialName) { mutableStateOf(initialName) }
    var career by remember(initialCareer) { mutableStateOf(initialCareer) }
    var strengths by remember(initialStrengths) { mutableStateOf(initialStrengths) }
    var projects by remember(initialProjects) { mutableStateOf(initialProjects) }
    val aiSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { dismissBubble(); onBackClick() }) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "뒤로가기",
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = "이력서 편집",
                    modifier = Modifier.weight(1f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { dismissBubble(); onSaveClick() }) {
                    Text("저장하기", color = PrimaryBlue, fontSize = 14.sp)
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { dismissBubble(); isPreviewMode = !isPreviewMode },
                            modifier = Modifier.weight(1f).height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                        ) {
                            Icon(
                                if (isPreviewMode) Icons.Default.Edit else Icons.Default.Visibility,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                if (isPreviewMode) "수정하기" else "미리보기",
                                color = Color.DarkGray,
                                fontSize = 14.sp
                            )
                        }
                        OutlinedButton(
                            onClick = { dismissBubble(); onPdfExportClick() },
                            modifier = Modifier.weight(1f).height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                        ) {
                            Icon(
                                Icons.Default.PictureAsPdf,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "PDF 내보내기",
                                color = Color.DarkGray,
                                fontSize = 14.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    PrimaryButton(
                        text = "실전 면접 보러가기",
                        onClick = { dismissBubble(); onInterviewClick() },
                    )
                }
                FloatingActionButton(
                    onClick = { dismissBubble(); showAiSheet = true },
                    containerColor = FabColor,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 20.dp)
                        .offset(y = (-46).dp)
                ) {
                    Icon(
                        Icons.Default.AutoAwesome,
                        contentDescription = "AI 피드백",
                        modifier = Modifier.size(24.dp)
                    )
                }
                if (showAiBubble) {
                    Card(
                        onClick = { dismissBubble() },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 88.dp)
                            .offset(y = (-46).dp),
                        shape = SpeechBubbleTailRightShape(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                    ) {
                        Text(
                            text = "AI 기반 피드백을 받아보세요",
                            modifier = Modifier.padding(start = 16.dp, end = 20.dp, top = 12.dp, bottom = 12.dp),
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            if (feedbackResultText.isNotBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F9FF)),
                    border = BorderStroke(1.dp, PrimaryBlue.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = feedbackResultTitle,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryBlue
                            )
                            TextButton(
                                onClick = {
                                    feedbackResultTitle = ""
                                    feedbackResultText = ""
                                },
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("닫기", color = Color.Gray, fontSize = 13.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = feedbackResultText,
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            lineHeight = 20.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            if (isPreviewMode) {
                if (items.isNotEmpty()) {
                    GeneratedResumeContentCard(items = items)
                } else {
                    PlaceholderResumeCard()
                }
            } else {
                Text(
                    "기본 정보",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                PrimaryTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "지원 포지션 / 성함",
                    placeholder = "이름 또는 포지션을 입력하세요",
                    modifier = Modifier.onFocusChanged { if (it.isFocused) dismissBubble() }
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "경력",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                OutlinedTextField(
                    value = career,
                    onValueChange = { career = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp)
                        .onFocusChanged { if (it.isFocused) dismissBubble() },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        cursorColor = PrimaryBlue
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "역량",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                OutlinedTextField(
                    value = strengths,
                    onValueChange = { strengths = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp)
                        .onFocusChanged { if (it.isFocused) dismissBubble() },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        cursorColor = PrimaryBlue
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "프로젝트",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                OutlinedTextField(
                    value = projects,
                    onValueChange = { projects = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp)
                        .onFocusChanged { if (it.isFocused) dismissBubble() },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        cursorColor = PrimaryBlue
                    )
                )
            }
        }
    }

    if (showAiSheet) {
        ModalBottomSheet(
            onDismissRequest = { showAiSheet = false },
            sheetState = aiSheetState,
            containerColor = Color.White,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Text(
                    "AI 피드백 받기",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "어떤 관점에서 이력서를 분석해 드릴까요?",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                FeedbackOptionCard(
                    title = "안티패턴 피드백",
                    description = "단순 나열, 근거 부족 등 흔한 문제점을 확인합니다.",
                    icon = Icons.Default.BugReport,
                    onClick = {
                        showAiSheet = false
                        showAntiPatternSheet = true
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                FeedbackOptionCard(
                    title = "면접관 페르소나 피드백",
                    description = "스타트업 CEO, 대기업 임원 등 특정 관점의 피드백을 받습니다.",
                    icon = Icons.Default.Groups,
                    onClick = {
                        showAiSheet = false
                        showPersonaSheet = true
                        viewModel.loadPersonas()
                    }
                )
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }

    if (showAntiPatternSheet) {
        ModalBottomSheet(
            onDismissRequest = { showAntiPatternSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = Color.White,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Text(
                    "안티패턴 피드백",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "확인할 항목을 선택하세요.",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                antiPatterns.forEach { item ->
                    SelectionItemCard(
                        title = item.title,
                        subtitle = item.description,
                        onClick = {
                            feedbackResultTitle = item.title
                            feedbackResultText = mockAntiPatternFeedback[item.title]
                                ?: "해당 항목에 대한 피드백을 불러오는 중입니다. (Mock)"
                            showAntiPatternSheet = false
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }

    if (showPersonaSheet) {
        ModalBottomSheet(
            onDismissRequest = { showPersonaSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = Color.White,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Text(
                    "면접관 페르소나 피드백",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "페르소나를 선택하세요.",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                when {
                    isLoadingPersonas -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("페르소나 목록을 불러오는 중...", color = Color.Gray, fontSize = 14.sp)
                        }
                    }
                    personasError != null -> {
                        Text(
                            text = personasError!!,
                            color = Color(0xFFDC2626),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        TextButton(onClick = { viewModel.loadPersonas() }) {
                            Text("다시 시도", color = PrimaryBlue)
                        }
                    }
                    else -> {
                        personas.forEach { persona ->
                            SelectionItemCard(
                                title = persona.title,
                                subtitle = persona.description,
                                onClick = {
                                    feedbackResultTitle = persona.title
                                    feedbackResultText = mockPersonaFeedback[persona.title]
                                        ?: "해당 페르소나 관점의 피드백을 불러오는 중입니다. (Mock)"
                                    showPersonaSheet = false
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ResumeEditScreenPreview() {
    ResumakerTheme {
        ResumeEditScreen(onBackClick = { })
    }
}

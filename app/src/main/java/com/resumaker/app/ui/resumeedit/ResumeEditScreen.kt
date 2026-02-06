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
import com.resumaker.app.component.card.ResumePreviewCard
import com.resumaker.app.component.card.SelectionItemCard
import com.resumaker.app.component.input.PrimaryTextField
import com.resumaker.app.ui.theme.ResumakerTheme
import org.koin.androidx.compose.koinViewModel

private val PrimaryBlue = Color(0xFF2161EE)
private val FabColor = Color(0xFF6366F1)
private val PrimaryDark = Color(0xFF0F172A)

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
    var isPreviewMode by remember { mutableStateOf(false) }
    var showAiSheet by remember { mutableStateOf(false) }
    var showAntiPatternSheet by remember { mutableStateOf(false) }
    var showPersonaSheet by remember { mutableStateOf(false) }
    var showAiBubble by remember { mutableStateOf(true) }

    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
    LaunchedEffect(Unit) {
        showAiBubble = !prefs.getBoolean(KEY_VISITED_RESUME_EDIT, false)
    }
    fun dismissBubble() {
        showAiBubble = false
        prefs.edit().putBoolean(KEY_VISITED_RESUME_EDIT, true).apply()
    }

    var name by remember { mutableStateOf("김민준") }
    var experience by remember { mutableStateOf("테크리드 솔루션즈 시니어 UI 디자이너 (2022~)\n• B2B SaaS 대시보드 리디자인 리드\n• 디자인 시스템 구축 및 컴포넌트 문서화") }
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
            if (isPreviewMode) {
                ResumePreviewCard()
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
                    label = "성함",
                    placeholder = "이름을 입력하세요",
                    modifier = Modifier.onFocusChanged { if (it.isFocused) dismissBubble() }
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "경력 사항",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                OutlinedTextField(
                    value = experience,
                    onValueChange = { experience = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp)
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
                        onClick = { /* TODO: API 요청 후 피드백 화면으로 */ showAntiPatternSheet = false }
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
                                onClick = { /* TODO: API 요청 후 피드백 화면으로 */ showPersonaSheet = false }
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

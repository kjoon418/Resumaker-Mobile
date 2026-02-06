package com.resumaker.app.ui.resumecreate

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.resumaker.app.component.button.PrimaryButton
import com.resumaker.app.component.input.PrimaryTextField
import com.resumaker.app.component.progress.StepProgressBar
import com.resumaker.app.component.section.InfoBanner
import com.resumaker.app.ui.theme.ResumakerTheme

private val PrimaryBlue = Color(0xFF2161EE)

private data class ExtraInfoItem(
    val id: String,
    val title: String,
    val content: String
)

private data class ProjectHistoryItem(
    val id: String,
    val projectName: String,
    val keyTasks: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ResumeDetailInputScreen(
    onBackClick: () -> Unit,
    onNext: () -> Unit,
    onFormatFileSelect: () -> Unit = {}
) {
    var resumeFormat by remember { mutableStateOf("") }
    var targetRole by remember { mutableStateOf("") }
    var slogan by remember { mutableStateOf("") }
    val strengthKeywords = remember { mutableStateListOf<String>() }
    val projectHistoryItems = remember { mutableStateListOf<ProjectHistoryItem>() }
    var collaborationStyle by remember { mutableStateOf("") }
    val techStacks = remember { mutableStateListOf("Java", "Spring Boot") }
    var futureGoals by remember { mutableStateOf("") }
    val extraItems = remember { mutableStateListOf<ExtraInfoItem>() }

    var showAddInfoDialog by remember { mutableStateOf(false) }
    var addInfoStep by remember { mutableStateOf(0) }
    var newInfoTitle by remember { mutableStateOf("") }
    var newInfoContent by remember { mutableStateOf("") }

    var showAddProjectDialog by remember { mutableStateOf(false) }
    var newProjectName by remember { mutableStateOf("") }
    var newProjectKeyTasks by remember { mutableStateOf("") }

    var showAddTechStackDialog by remember { mutableStateOf(false) }
    var newTechStackName by remember { mutableStateOf("") }

    var newKeyword by remember { mutableStateOf("") }
    var showAddKeyword by remember { mutableStateOf(false) }

    // TODO: 서버 API를 통해 1단계에서 분석된 데이터를 가져와 상태를 업데이트하는 로직 구현 필요
    // LaunchedEffect(Unit) { fetchAnalyzedData() }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "뒤로가기",
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = "새 이력서 생성",
                    modifier = Modifier.weight(1f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                    Spacer(modifier = Modifier.size(48.dp))
            }
        },
        bottomBar = {
            Column(modifier = Modifier.padding(20.dp)) {
                PrimaryButton(
                    text = "다음 단계로",
                    onClick = onNext
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            StepProgressBar(currentStep = 2, stepTitle = "정보 입력")

            Spacer(modifier = Modifier.height(24.dp))

            // TODO: 서버로부터 데이터를 입력받았을 때만 표시
            InfoBanner(
                text = "AI 분석 완료! 기존 이력서 분석을 통해 항목을 자동으로 채웠습니다. 내용을 확인하고 필요시 수정해 주세요."
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "이력서 형식",
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = onFormatFileSelect) {
                    Text(
                        "파일로 전달하기",
                        fontSize = 14.sp,
                        color = PrimaryBlue
                    )
                }
            }
            OutlinedTextField(
                value = resumeFormat,
                onValueChange = { resumeFormat = it },
                placeholder = {
                    Text(
                        "이력서의 양식을 설명해주세요. (예: 이름, 자기소개, 기술 스택)",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    cursorColor = PrimaryBlue
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryTextField(
                value = targetRole,
                onValueChange = { targetRole = it },
                label = "전문 분야 및 타겟",
                placeholder = "예: 시니어 백엔드 엔지니어 / 금융권 도메인"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "핵심 슬로건 및 가치관",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = slogan,
                onValueChange = { slogan = it },
                placeholder = {
                    Text(
                        "본인을 가장 잘 나타내는 한 줄 슬로건을 입력하세요.",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    cursorColor = PrimaryBlue
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "본인의 강점(키워드)",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                strengthKeywords.forEach { keyword ->
                    SuggestionChip(
                        onClick = { strengthKeywords.remove(keyword) },
                        label = { Text(keyword) },
                        shape = RoundedCornerShape(20.dp)
                    )
                }
                InputChip(
                    selected = false,
                    onClick = { showAddKeyword = true },
                    label = { Text("+ 키워드 추가") }
                )
            }
            if (showAddKeyword) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newKeyword,
                        onValueChange = { newKeyword = it },
                        placeholder = { Text("키워드 입력", color = Color.LightGray, fontSize = 14.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 48.dp),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryBlue,
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            cursorColor = PrimaryBlue
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {
                        if (newKeyword.isNotBlank()) {
                            strengthKeywords.add(newKeyword.trim())
                            newKeyword = ""
                            showAddKeyword = false
                        }
                    }) {
                        Text("추가", color = PrimaryBlue)
                    }
                    TextButton(onClick = { showAddKeyword = false; newKeyword = "" }) {
                        Text("취소", color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "프로젝트 이력(핵심 과업)",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            projectHistoryItems.forEach { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(
                            text = item.projectName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        if (item.keyTasks.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.keyTasks,
                                fontSize = 13.sp,
                                color = Color.Gray
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = { projectHistoryItems.removeAll { it.id == item.id } },
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "삭제",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
            OutlinedButton(
                onClick = {
                    showAddProjectDialog = true
                    newProjectName = ""
                    newProjectKeyTasks = ""
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("프로젝트 추가", color = Color.Gray, fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "선호하는 협업 방식",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = collaborationStyle,
                onValueChange = { collaborationStyle = it },
                placeholder = {
                    Text(
                        "예: 애자일 스프린트, 주간 동기화, 문서 공유 중심 등",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    cursorColor = PrimaryBlue
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "기술 스택",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                techStacks.forEach { stack ->
                    SuggestionChip(
                        onClick = { techStacks.remove(stack) },
                        label = { Text(stack) },
                        shape = RoundedCornerShape(20.dp)
                    )
                }
                InputChip(
                    selected = false,
                    onClick = { showAddTechStackDialog = true },
                    label = { Text("+ 추가") }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "미래 지향점",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = futureGoals,
                onValueChange = { futureGoals = it },
                placeholder = {
                    Text(
                        "앞으로의 목표, 성장 방향, 관심 분야 등을 입력하세요.",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    cursorColor = PrimaryBlue
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "추가 정보",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            extraItems.forEach { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            if (item.content.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.content,
                                    fontSize = 13.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                        IconButton(
                            onClick = { extraItems.removeAll { it.id == item.id } },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "삭제",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Gray
                            )
                        }
                    }
                }
            }
            OutlinedButton(
                onClick = {
                    showAddInfoDialog = true
                    addInfoStep = 0
                    newInfoTitle = ""
                    newInfoContent = ""
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("정보 추가하기", color = Color.Gray, fontSize = 15.sp)
            }

            if (showAddInfoDialog) {
                Dialog(onDismissRequest = { showAddInfoDialog = false }) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (addInfoStep == 0) "항목 제목 입력" else "항목 내용 입력",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            if (addInfoStep == 0) {
                                OutlinedTextField(
                                    value = newInfoTitle,
                                    onValueChange = { newInfoTitle = it },
                                    placeholder = { Text("항목 제목", color = Color.LightGray) },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = PrimaryBlue,
                                        unfocusedBorderColor = Color(0xFFE0E0E0),
                                        cursorColor = PrimaryBlue
                                    )
                                )
                            } else {
                                Text(
                                    text = newInfoTitle,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp)
                                )
                                OutlinedTextField(
                                    value = newInfoContent,
                                    onValueChange = { newInfoContent = it },
                                    placeholder = {
                                        Text(
                                            "항목 내용을 입력하세요",
                                            color = Color.LightGray
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 100.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = PrimaryBlue,
                                        unfocusedBorderColor = Color(0xFFE0E0E0),
                                        cursorColor = PrimaryBlue
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (addInfoStep == 1) {
                                    OutlinedButton(
                                        onClick = {
                                            addInfoStep = 0
                                            newInfoContent = ""
                                        },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("이전")
                                    }
                                }
                                TextButton(
                                    onClick = { showAddInfoDialog = false },
                                    modifier = Modifier.weight(if (addInfoStep == 1) 1f else 2f)
                                ) {
                                    Text("취소", color = Color.Gray)
                                }
                                if (addInfoStep == 0) {
                                    Button(
                                        onClick = {
                                            if (newInfoTitle.isNotBlank()) addInfoStep = 1
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = PrimaryBlue
                                        )
                                    ) {
                                        Text("다음")
                                    }
                                } else {
                                    Button(
                                        onClick = {
                                            extraItems.add(
                                                ExtraInfoItem(
                                                    id = "extra-${System.currentTimeMillis()}",
                                                    title = newInfoTitle.trim(),
                                                    content = newInfoContent.trim()
                                                )
                                            )
                                            showAddInfoDialog = false
                                            addInfoStep = 0
                                            newInfoTitle = ""
                                            newInfoContent = ""
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = PrimaryBlue
                                        )
                                    ) {
                                        Text("추가")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (showAddProjectDialog) {
                Dialog(onDismissRequest = { showAddProjectDialog = false }) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "프로젝트 이력 추가",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            OutlinedTextField(
                                value = newProjectName,
                                onValueChange = { newProjectName = it },
                                placeholder = { Text("프로젝트명", color = Color.LightGray) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = PrimaryBlue,
                                    unfocusedBorderColor = Color(0xFFE0E0E0),
                                    cursorColor = PrimaryBlue
                                )
                            )
                            OutlinedTextField(
                                value = newProjectKeyTasks,
                                onValueChange = { newProjectKeyTasks = it },
                                placeholder = {
                                    Text(
                                        "핵심 과업 및 성과",
                                        color = Color.LightGray
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 80.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = PrimaryBlue,
                                    unfocusedBorderColor = Color(0xFFE0E0E0),
                                    cursorColor = PrimaryBlue
                                )
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                TextButton(
                                    onClick = { showAddProjectDialog = false },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("취소", color = Color.Gray)
                                }
                                Button(
                                    onClick = {
                                        if (newProjectName.isNotBlank()) {
                                            projectHistoryItems.add(
                                                ProjectHistoryItem(
                                                    id = "proj-${System.currentTimeMillis()}",
                                                    projectName = newProjectName.trim(),
                                                    keyTasks = newProjectKeyTasks.trim()
                                                )
                                            )
                                            showAddProjectDialog = false
                                            newProjectName = ""
                                            newProjectKeyTasks = ""
                                        }
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = PrimaryBlue
                                    )
                                ) {
                                    Text("추가")
                                }
                            }
                        }
                    }
                }
            }

            if (showAddTechStackDialog) {
                Dialog(onDismissRequest = { showAddTechStackDialog = false }) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "기술 스택 추가",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            OutlinedTextField(
                                value = newTechStackName,
                                onValueChange = { newTechStackName = it },
                                placeholder = { Text("기술 스택 이름 입력", color = Color.LightGray) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = PrimaryBlue,
                                    unfocusedBorderColor = Color(0xFFE0E0E0),
                                    cursorColor = PrimaryBlue
                                )
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                TextButton(
                                    onClick = { showAddTechStackDialog = false },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("취소", color = Color.Gray)
                                }
                                Button(
                                    onClick = {
                                        val name = newTechStackName.trim()
                                        if (name.isNotBlank()) {
                                            techStacks.add(name)
                                            showAddTechStackDialog = false
                                            newTechStackName = ""
                                        }
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = PrimaryBlue
                                    )
                                ) {
                                    Text("추가")
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResumeDetailInputScreenPreview() {
    ResumakerTheme {
        ResumeDetailInputScreen(
            onBackClick = { },
            onNext = { }
        )
    }
}

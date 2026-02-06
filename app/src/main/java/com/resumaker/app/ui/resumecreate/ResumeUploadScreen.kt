package com.resumaker.app.ui.resumecreate

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.component.button.PrimaryButton
import com.resumaker.app.component.progress.StepProgressBar
import com.resumaker.app.ui.theme.ResumakerTheme

private val PrimaryBlue = Color(0xFF2161EE)
private val UploadAreaBackground = Color(0xFFF8FAFC)
private val UploadAreaBorder = Color(0xFFCBD5E1)
private val UploadedCardBackground = Color(0xFFF1F5F9)

@Composable
fun ResumeUploadScreen(
    onBackClick: () -> Unit,
    onFileSelect: () -> Unit,
    onSkip: () -> Unit,
    onNext: () -> Unit,
    onRemoveUpload: (() -> Unit)? = null,
    uploadedFileName: String? = null
) {
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
                // 상단 바 오른쪽 정렬을 위해 투명 버튼 또는 Spacer (타이틀 중앙 느낌 유지)
                Spacer(modifier = Modifier.size(48.dp))
            }
        },
        bottomBar = {
            Column(modifier = Modifier.padding(20.dp)) {
                if (uploadedFileName == null) {
                    Text(
                        text = "건너뛰면 직접 모든 정보를 직접 입력하게 됩니다.",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = onSkip,
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                    ) {
                        Text(
                            "건너뛰기",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                } else {
                    PrimaryButton(
                        text = "다음 단계",
                        onClick = onNext,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
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
            StepProgressBar(
                currentStep = 1,
                totalSteps = 4,
                stepTitle = "기존 이력서 제출"
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "기존 이력서가 있나요?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "PDF 파일을 업로드하면 AI가 내용을 분석해\n다음 단계를 자동으로 채워드려요.",
                fontSize = 16.sp,
                color = Color.Gray,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(16.dp),
                color = UploadAreaBackground,
                border = BorderStroke(1.dp, UploadAreaBorder)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.PictureAsPdf,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = PrimaryBlue
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "PDF 파일 업로드",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onFileSelect,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("파일 선택")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "PDF, 최대 10MB",
                        color = Color.LightGray,
                        fontSize = 11.sp
                    )
                }
            }

            if (uploadedFileName != null) {
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = UploadedCardBackground)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Description,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                uploadedFileName,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                            Text(
                                "2.4MB • 2026.02 업로드",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        IconButton(onClick = { onRemoveUpload?.invoke() }) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "삭제",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResumeUploadScreenPreview() {
    ResumakerTheme {
        ResumeUploadScreen(
            onBackClick = { },
            onFileSelect = { },
            onSkip = { },
            onNext = { },
            uploadedFileName = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResumeUploadScreenWithFilePreview() {
    ResumakerTheme {
        ResumeUploadScreen(
            onBackClick = { },
            onFileSelect = { },
            onSkip = { },
            onNext = { },
            onRemoveUpload = { },
            uploadedFileName = "이력서_홍길동.pdf"
        )
    }
}

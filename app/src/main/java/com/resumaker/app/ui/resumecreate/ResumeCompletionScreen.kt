package com.resumaker.app.ui.resumecreate

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.component.button.PrimaryButton
import com.resumaker.app.component.progress.StepProgressBar
import com.resumaker.app.ui.theme.ResumakerTheme

private val PrimaryBlue = Color(0xFF2161EE)
private val PreviewCardBackground = Color(0xFF0F172A)
private val SectionAccent = Color(0xFF6366F1)

@Composable
fun ResumeCompletionScreen(
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCloseClick: () -> Unit = {}
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
                    text = "이력서 완성",
                    modifier = Modifier.weight(1f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onCloseClick) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "닫기",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onSaveClick,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Icon(
                        Icons.Default.SaveAlt,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("저장하기", color = Color.DarkGray)
                }
                PrimaryButton(
                    text = "수정하러 가기",
                    onClick = onEditClick,
                    modifier = Modifier.weight(1f)
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
            StepProgressBar(currentStep = 4, stepTitle = "완성")

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color(0xFFF1F5F9), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "이력서가 완성되었습니다!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "AI가 입력하신 정보를 바탕으로\n최적의 이력서를 생성했습니다.",
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // TODO: 서버에서 받아온 이력서 데이터를 렌더링하는 영역. 현재는 하드코딩된 미리보기.
            ResumePreviewCard()

            Spacer(modifier = Modifier.height(24.dp))

            InfoBanner(
                text = "생성된 내용은 자유롭게 수정할 수 있으며, PDF로 내보내기가 가능합니다."
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ResumePreviewCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PreviewCardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "김지훈",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Senior Backend Engineer",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                }
                Surface(
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    color = Color.Gray
                ) {}
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "SUMMARY",
                color = SectionAccent,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "\"복잡한 비즈니스 로직을 단순화하고, 확장 가능한 시스템을 설계하는 아키텍트입니다.\"",
                color = Color.White,
                fontSize = 15.sp,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "TECH STACK",
                color = SectionAccent,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Java, Spring Boot, Go, AWS, Kubernetes, Redis",
                color = Color.White,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResumeCompletionScreenPreview() {
    ResumakerTheme {
        ResumeCompletionScreen(
            onBackClick = { },
            onEditClick = { },
            onSaveClick = { }
        )
    }
}

package com.resumaker.app.component.input

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.ui.theme.ResumakerTheme

private val PrimaryBlue = Color(0xFF2161EE)
private val BackgroundLight = Color(0xFFF8F9FA)
private val BorderLight = Color(0xFFE2E8F0)

/**
 * 가상 면접 하단: 툴바 버튼(힌트, 이력서 참고, 답변 가이드) + 입력창 + 전송 버튼.
 */
@Composable
fun InterviewBottomSection(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val tools = listOf("💡 힌트 보기", "📝 내 이력서 참고", "⚡ 답변 가이드")
            tools.forEach { tool ->
                OutlinedButton(
                    onClick = { /* TODO: 툴바 액션 */ },
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    border = BorderStroke(1.dp, BorderLight)
                ) {
                    Text(tool, fontSize = 11.sp, color = Color.DarkGray)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Surface(
                modifier = Modifier.weight(1f),
                color = BackgroundLight,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, BorderLight)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 40.dp),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 14.sp,
                            color = Color.Black
                        ),
                        cursorBrush = SolidColor(PrimaryBlue),
                        decorationBox = { innerTextField ->
                            if (value.isEmpty()) {
                                Text(
                                    "답변을 입력하세요...",
                                    color = Color.LightGray,
                                    fontSize = 14.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Icon(
                                Icons.Default.Mic,
                                contentDescription = "음성 입력",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Icon(
                                Icons.Default.Image,
                                contentDescription = "이미지 첨부",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            "${value.length} / 1000",
                            fontSize = 10.sp,
                            color = Color.LightGray
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            FloatingActionButton(
                onClick = onSend,
                containerColor = PrimaryBlue,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Send, contentDescription = "전송")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InterviewBottomSectionEmptyPreview() {
    ResumakerTheme {
        InterviewBottomSection(
            value = "",
            onValueChange = { },
            onSend = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InterviewBottomSectionWithTextPreview() {
    ResumakerTheme {
        InterviewBottomSection(
            value = "저는 3년 차 프론트엔드 개발자입니다.",
            onValueChange = { },
            onSend = { }
        )
    }
}

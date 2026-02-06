package com.resumaker.app.component.chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.ui.theme.ResumakerTheme

private val PrimaryBlue = Color(0xFF2161EE)
private val BorderLight = Color(0xFFE2E8F0)

/**
 * AI 면접관 메시지 말풍선.
 * 좌측 정렬, 흰 배경, "AI 면접관" 라벨 + 시간 표시.
 */
@Composable
fun AiMessageBubble(text: String, time: String) {
    Column(modifier = Modifier.fillMaxWidth(0.8f)) {
        Text(
            "AI 면접관",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            ),
            border = BorderStroke(1.dp, BorderLight)
        ) {
            Text(
                text,
                modifier = Modifier.padding(12.dp),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
        Text(
            time,
            fontSize = 10.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AiMessageBubblePreview() {
    ResumakerTheme {
        AiMessageBubble(
            text = "안녕하세요, 지원자님. Resumaker 가상 면접에 오신 것을 환영합니다. 먼저 간단하게 자기소개 부탁드립니다.",
            time = "오전 10:02"
        )
    }
}

/**
 * 사용자 메시지 말풍선.
 * 우측 정렬, 파란 배경, 시간 표시.
 */
@Composable
fun UserMessageBubble(text: String, time: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(0.75f),
            color = PrimaryBlue,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 0.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            )
        ) {
            Text(
                text,
                modifier = Modifier.padding(12.dp),
                color = Color.White,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
        Text(
            time,
            fontSize = 10.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserMessageBubblePreview() {
    ResumakerTheme {
        UserMessageBubble(
            text = "안녕하세요. 저는 3년 차 프론트엔드 개발자로서...",
            time = "오전 10:03"
        )
    }
}

package com.resumaker.app.component.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.ui.theme.ResumakerTheme

private val SubtitleGray = Color(0xFF64748B)

/**
 * 프로필 상단 헤더. 이름(큰 글씨) + 부제목(직무 등).
 * 마이페이지, 설정 등에서 재사용 가능.
 */
@Composable
fun ProfileHeaderSection(
    name: String,
    job: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = job,
            fontSize = 14.sp,
            color = SubtitleGray
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileHeaderSectionPreview() {
    ResumakerTheme {
        ProfileHeaderSection(name = "홍길동", job = "프론트엔드 개발자")
    }
}

package com.resumaker.app.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.resumaker.app.model.UserProfile
import com.resumaker.app.ui.theme.ResumakerTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp

private val LabelGray = Color(0xFF94A3B8)

/**
 * UserProfile 기본 정보를 2열 그리드로 표시.
 * 마이페이지 기본 정보 섹션에서 사용.
 */
@Composable
fun BasicInfoGrid(
    user: UserProfile,
    modifier: Modifier = Modifier
) {
    val infoItems = listOf(
        "성명" to user.name,
        "나이" to "${user.age}세",
        "이메일" to user.email,
        "성별" to if (user.gender == "MALE") "남성 (MALE)" else "여성 (FEMALE)",
        "직업" to user.job,
        "연락처" to user.phoneNumber
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        infoItems.chunked(2).forEach { rowItems ->
            Row(modifier = Modifier.fillMaxWidth()) {
                rowItems.forEach { item ->
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.first,
                            fontSize = 12.sp,
                            color = LabelGray
                        )
                        Text(
                            text = item.second,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BasicInfoGridPreview() {
    ResumakerTheme {
        BasicInfoGrid(
            user = UserProfile(
                name = "홍길동",
                email = "hong@example.com",
                age = 28,
                gender = "MALE",
                job = "프론트엔드 개발자",
                phoneNumber = "010-1234-5678"
            )
        )
    }
}

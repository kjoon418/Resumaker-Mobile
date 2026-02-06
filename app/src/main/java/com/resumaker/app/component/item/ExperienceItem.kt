package com.resumaker.app.component.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.model.Experience
import com.resumaker.app.ui.theme.ResumakerTheme

private val BodyGray = Color(0xFF64748B)
private val DarkGray = Color(0xFF475569)
private val CurrentBadgeBg = Color(0xFFE0F2FE)
private val CurrentBadgeText = Color(0xFF0369A1)

/**
 * 경력 한 건 표시. 재직 중일 경우 뱃지 표시.
 * 마이페이지, 이력서 상세 등에서 재사용 가능.
 */
@Composable
fun ExperienceItem(
    exp: Experience,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = exp.company,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (exp.isCurrent) {
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    color = CurrentBadgeBg,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "재직 중",
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontSize = 10.sp,
                        color = CurrentBadgeText,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
        Text(
            text = "${exp.role} · ${exp.period}",
            fontSize = 13.sp,
            color = BodyGray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = exp.description,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = DarkGray
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExperienceItemPreview() {
    ResumakerTheme {
        ExperienceItem(
            exp = Experience(
                company = "(주)테크컴퍼니",
                role = "시니어 프론트엔드 개발자",
                period = "2022.01 ~ 현재",
                description = "React 기반 웹 서비스 개발 및 팀 리드.",
                isCurrent = true
            )
        )
    }
}

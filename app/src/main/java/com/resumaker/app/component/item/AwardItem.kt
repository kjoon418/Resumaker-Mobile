package com.resumaker.app.component.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.model.Award
import com.resumaker.app.ui.theme.ResumakerTheme

private val BodyGray = Color(0xFF64748B)
private val DarkGray = Color(0xFF475569)

/**
 * 수상 경력 한 건 표시. 마이페이지, 이력서 상세 등에서 재사용 가능.
 */
@Composable
fun AwardItem(
    award: Award,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 12.dp)) {
        Text(
            text = award.title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "${award.issuer} · ${award.year}",
            fontSize = 13.sp,
            color = BodyGray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = award.content,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = DarkGray
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AwardItemPreview() {
    ResumakerTheme {
        AwardItem(
            award = Award(
                title = "우수 사원상",
                content = "연간 성과 평가 우수",
                year = "2023",
                issuer = "테크컴퍼니"
            )
        )
    }
}

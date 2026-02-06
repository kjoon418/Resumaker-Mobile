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
import com.resumaker.app.model.Education
import com.resumaker.app.ui.theme.ResumakerTheme

private val BodyGray = Color(0xFF64748B)
private val DarkGray = Color(0xFF475569)

/**
 * 학력 한 건 표시. 마이페이지, 이력서 상세 등에서 재사용 가능.
 */
@Composable
fun EducationItem(
    edu: Education,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 12.dp)) {
        Text(
            text = edu.school,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "${edu.major} · ${edu.period}",
            fontSize = 13.sp,
            color = BodyGray
        )
        if (edu.score.isNotBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "학점: ${edu.score}",
                fontSize = 13.sp,
                color = DarkGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EducationItemPreview() {
    ResumakerTheme {
        EducationItem(
            edu = Education(
                school = "서울대학교",
                major = "컴퓨터공학과",
                period = "2014.03 ~ 2018.02",
                score = "4.2/4.5"
            )
        )
    }
}

package com.resumaker.feature.mypage.component.item

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.domain.common.model.Education

private val BodyGray = Color(0xFF64748B)
private val DarkGray = Color(0xFF475569)

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

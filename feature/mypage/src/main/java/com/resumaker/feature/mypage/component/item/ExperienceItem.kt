package com.resumaker.feature.mypage.component.item

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.domain.common.model.Experience

private val BodyGray = Color(0xFF64748B)
private val DarkGray = Color(0xFF475569)
private val CurrentBadgeBg = Color(0xFFE0F2FE)
private val CurrentBadgeText = Color(0xFF0369A1)

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

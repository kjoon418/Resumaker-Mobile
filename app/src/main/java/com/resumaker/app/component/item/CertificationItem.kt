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
import com.resumaker.app.model.Certification
import com.resumaker.app.ui.theme.ResumakerTheme

private val BodyGray = Color(0xFF64748B)
private val DarkGray = Color(0xFF475569)

/**
 * 자격증 한 건 표시. 마이페이지, 이력서 상세 등에서 재사용 가능.
 */
@Composable
fun CertificationItem(
    cert: Certification,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 12.dp)) {
        Text(
            text = cert.name,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "${cert.issuer} · ${cert.date}",
            fontSize = 13.sp,
            color = BodyGray
        )
        if (cert.score != null && cert.score.isNotBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "점수: ${cert.score}",
                fontSize = 13.sp,
                color = DarkGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CertificationItemPreview() {
    ResumakerTheme {
        CertificationItem(
            cert = Certification(
                name = "정보처리기사",
                issuer = "한국산업인력공단",
                date = "2020.05",
                score = "1차 합격"
            )
        )
    }
}

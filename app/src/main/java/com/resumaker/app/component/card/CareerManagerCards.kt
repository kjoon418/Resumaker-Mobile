package com.resumaker.app.component.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
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
import com.resumaker.app.model.Persona
import com.resumaker.app.model.Resume
import com.resumaker.app.ui.theme.ResumakerTheme

private val CardIconBackground = Color(0xFFF1F5F9)

@Composable
fun ResumeCard(
    resume: Resume,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    SectionCard(modifier = modifier, onClick = onClick) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = CardIconBackground
            ) {}
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = resume.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "최종 수정: ${resume.lastModified}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun PersonaCard(
    persona: Persona,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    SectionCard(modifier = modifier, onClick = onClick) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = CardIconBackground
            ) {}
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = persona.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = persona.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp,
                    maxLines = 2
                )
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// --- Previews ---

private val previewResume = Resume(
    id = "1",
    title = "프론트엔드 개발자 이력서",
    lastModified = "2025.02.05",
    iconType = "doc"
)

private val previewPersona = Persona(
    id = "1",
    title = "친절한 면접관",
    description = "많은 피드백을 주며 대화를 이끌어 줍니다.",
    iconType = "persona1"
)

@Preview(showBackground = true)
@Composable
private fun ResumeCardPreview() {
    ResumakerTheme {
        ResumeCard(resume = previewResume)
    }
}

@Preview(showBackground = true)
@Composable
private fun PersonaCardPreview() {
    ResumakerTheme {
        PersonaCard(persona = previewPersona)
    }
}

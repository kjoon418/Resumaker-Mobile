package com.resumaker.app.component.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.resumaker.app.ui.theme.ResumakerTheme

/**
 * 목록/섹션용 공통 카드 스타일 (둥근 모서리, 부드러운 그림자).
 * ResumeCard, PersonaCard 등에서 재사용.
 * @param onClick null이면 클릭 불가, 지정 시 카드 전체 클릭 가능
 */
@Composable
fun SectionCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val clickModifier = if (onClick != null) {
        Modifier.clickable(
            role = Role.Button,
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { onClick!!() }
    } else Modifier

    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(clickModifier),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun SectionCardPreview() {
    ResumakerTheme {
        SectionCard {
            Box(Modifier.padding(16.dp)) {
                Text("카드 내용 예시", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SectionCardClickablePreview() {
    ResumakerTheme {
        SectionCard(onClick = { }) {
            Box(Modifier.padding(16.dp)) {
                Text("클릭 가능한 카드", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

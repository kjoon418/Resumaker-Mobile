package com.resumaker.feature.mypage.component.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.core.ui.card.SectionCard

private val SectionTitleGray = Color(0xFF64748B)
private val EditIconTint = Color(0xFF9ba5b5)

@Composable
fun InfoSectionCard(
    title: String,
    modifier: Modifier = Modifier,
    onEditClick: (() -> Unit)? = null,
    onAddClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    SectionCard(modifier = modifier) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = SectionTitleGray
                )
                if (onEditClick != null) {
                    IconButton(
                        onClick = onEditClick,
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "수정",
                            tint = EditIconTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                if (onAddClick != null) {
                    IconButton(
                        onClick = onAddClick,
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "추가",
                            tint = EditIconTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            content()
        }
    }
}

package com.resumaker.app.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.R
import com.resumaker.app.component.button.SecondaryActionButton
import com.resumaker.app.model.Persona
import com.resumaker.app.model.Resume
import com.resumaker.app.ui.theme.ResumakerTheme

private val CardIconBackground = Color(0xFFF1F5F9)
private val ListCardIconBackground = Color(0xFFEEF2FF)
private val ListCardIconTint = Color(0xFF6366F1)
/** 이름 변경(연필) 아이콘 색상 - 연한 청회색 */
private val RenameIconTint = Color(0xFF9ba5b5)
/** 삭제(쓰레기통) 아이콘 색상 - 연한 청회색 */
private val DeleteIconTint = Color(0xFF9ba5b5)

private fun iconForResumeType(iconType: String): ImageVector = when (iconType) {
    "code" -> Icons.Default.Code
    "lang" -> Icons.Default.Translate
    "design" -> Icons.Default.Palette
    "db" -> Icons.Default.Storage
    else -> Icons.Default.Description
}

@Composable
fun ResumeCard(
    resume: Resume,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    SectionCard(modifier = modifier, onClick = onClick) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = resume.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
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
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    SectionCard(modifier = modifier, onClick = onClick) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
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

/**
 * 이력서 목록 화면용 카드. 아이콘, 제목, 수정일, 이름 변경/삭제 버튼, 수정하기/PDF 다운 버튼 포함.
 */
@Composable
fun ResumeDetailCard(
    resume: Resume,
    onEdit: () -> Unit,
    onPdfDownload: () -> Unit,
    onRenameClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SectionCard(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (resume.iconType == "doc") {
                        Image(
                            painter = painterResource(R.drawable.dev),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            iconForResumeType(resume.iconType),
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = ListCardIconTint
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = resume.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${resume.lastModified} 수정",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    IconButton(
                        onClick = onRenameClick,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "이름 변경",
                            tint = RenameIconTint,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    IconButton(
                        onClick = onDeleteClick,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "삭제",
                            tint = DeleteIconTint,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SecondaryActionButton(
                    text = "수정하기",
                    onClick = onEdit,
                    modifier = Modifier.weight(1f),
                    containerColor = Color(0xFF2161EE),
                    contentColor = Color.White
                )
                SecondaryActionButton(
                    text = "PDF 다운",
                    onClick = onPdfDownload,
                    modifier = Modifier.weight(1f)
                )
            }
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
        ResumeCard(
            resume = previewResume,
                icon = {
                Image(
                    painter = painterResource(R.drawable.dev),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PersonaCardPreview() {
    ResumakerTheme {
        PersonaCard(
            persona = previewPersona,
                icon = {
                Image(
                    painter = painterResource(R.drawable.warm),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResumeDetailCardPreview() {
    ResumakerTheme {
        ResumeDetailCard(
            resume = previewResume,
            onEdit = { },
            onPdfDownload = { },
            onRenameClick = { },
            onDeleteClick = { }
        )
    }
}

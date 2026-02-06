package com.resumaker.app.component.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.ui.theme.ResumakerTheme

private val StepCompletedColor = Color(0xFF2161EE)
private val StepPendingColor = Color(0xFFE2E8F0)
private val StepTextColor = Color(0xFF2161EE)
private val StepTitleColor = Color.Gray

/**
 * 새 이력서 생성 등 단계별 흐름에서 사용하는 진척도 바.
 * @param currentStep 현재 단계 (1-based)
 * @param totalSteps 전체 단계 수 (기본 4)
 * @param stepTitle 현재 단계 제목 (예: "기존 이력서 제출")
 */
@Composable
fun StepProgressBar(
    currentStep: Int,
    totalSteps: Int = 4,
    stepTitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(totalSteps) { index ->
                    val isCompleted = index < currentStep
                    Box(
                        modifier = Modifier
                            .size(width = 32.dp, height = 6.dp)
                            .background(
                                color = if (isCompleted) StepCompletedColor else StepPendingColor,
                                shape = RoundedCornerShape(3.dp)
                            )
                    )
                }
            }
            Text(
                text = "$currentStep/$totalSteps 단계",
                fontSize = 14.sp,
                color = StepTextColor,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stepTitle,
            fontSize = 14.sp,
            color = StepTitleColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StepProgressBarPreview() {
    ResumakerTheme {
        StepProgressBar(
            currentStep = 1,
            totalSteps = 4,
            stepTitle = "기존 이력서 제출"
        )
    }
}

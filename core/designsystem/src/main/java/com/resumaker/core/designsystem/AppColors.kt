package com.resumaker.core.designsystem

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * 앱 고유의 색상 팔레트.
 * MaterialTheme을 넘어 비즈니스 디자인 언어를 보호합니다.
 */
object AppColors {
    val PrimaryBlue = Color(0xFF2161EE)
    val PrimaryDark = Color(0xFF0F172A)
    val FabColor = Color(0xFF6366F1)
    val CardIconBackground = Color(0xFFF1F5F9)
    val ListCardIconBackground = Color(0xFFEEF2FF)
    val ListCardIconTint = Color(0xFF6366F1)
    val RenameIconTint = Color(0xFF9ba5b5)
    val DeleteIconTint = Color(0xFF9ba5b5)
    val BorderLight = Color(0xFFE2E8F0)
    val SurfaceLight = Color(0xFFF1F5F9)
    val ScreenBackground = Color(0xFFF8FAFC)
    val ActionIconTint = Color.Gray
    val PromptBoxBackground = Color(0xFFF1F5F9)
    val FeedbackCardBackground = Color(0xFFF0F9FF)
    val ErrorRed = Color(0xFFDC2626)
    val TextSecondary = Color.DarkGray
}

/** CompositionLocal: 앱 색상 팔레트 */
val LocalAppColors = staticCompositionLocalOf { AppColors }

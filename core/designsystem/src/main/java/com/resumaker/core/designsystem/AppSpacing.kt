package com.resumaker.core.designsystem

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 앱 고유의 간격 시스템.
 * 일관된 레이아웃을 위한 표준 간격 값입니다.
 */
object AppSpacing {
    val xs: Dp = 4.dp
    val sm: Dp = 8.dp
    val md: Dp = 12.dp
    val lg: Dp = 16.dp
    val xl: Dp = 20.dp
    val xxl: Dp = 24.dp
    val xxxl: Dp = 32.dp

    /** 화면 좌우 기본 패딩 */
    val screenHorizontal: Dp = 20.dp

    /** 화면 상하 기본 패딩 */
    val screenVertical: Dp = 24.dp

    /** 카드 내부 패딩 */
    val cardPadding: Dp = 16.dp

    /** 섹션 간 간격 */
    val sectionSpacing: Dp = 24.dp

    /** 버튼 높이 */
    val buttonHeight: Dp = 56.dp
}

/** CompositionLocal: 앱 간격 시스템 */
val LocalAppSpacing = staticCompositionLocalOf { AppSpacing }

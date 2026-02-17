package com.resumaker.core.designsystem

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * Modifier 확장 함수.
 * '어떻게(How)'가 아닌 '무엇(What)'을 하는지 설명하여 가독성을 확보합니다.
 */

/** 화면 표준 패딩 적용 */
fun Modifier.standardScreenPadding(): Modifier = composed {
    val spacing = LocalAppSpacing.current
    padding(
        horizontal = spacing.screenHorizontal,
        vertical = spacing.screenVertical
    )
}

/** 카드 내부 표준 패딩 적용 */
fun Modifier.standardCardPadding(): Modifier = composed {
    padding(LocalAppSpacing.current.cardPadding)
}

/** 섹션 간 표준 간격 적용 (상단) */
fun Modifier.sectionSpacing(): Modifier = composed {
    padding(top = LocalAppSpacing.current.sectionSpacing)
}

/** 작은 간격 적용 */
fun Modifier.smallPadding(): Modifier = composed {
    padding(LocalAppSpacing.current.sm)
}

/** 중간 간격 적용 */
fun Modifier.mediumPadding(): Modifier = composed {
    padding(LocalAppSpacing.current.md)
}

/** 큰 간격 적용 */
fun Modifier.largePadding(): Modifier = composed {
    padding(LocalAppSpacing.current.lg)
}

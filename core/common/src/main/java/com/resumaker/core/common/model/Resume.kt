package com.resumaker.core.common.model

import androidx.compose.runtime.Immutable
import com.resumaker.core.designsystem.IconType

/**
 * 이력서 목록/카드에 표시할 이력서 데이터.
 * @param iconType drawable과 연결된 아이콘 타입
 */
@Immutable
data class Resume(
    val id: String,
    val title: String,
    val lastModified: String,
    val iconType: IconType = IconType.Dev
) {
    constructor(
        id: String,
        title: String,
        lastModified: String,
        iconTypeValue: String
    ) : this(id, title, lastModified, IconType.fromString(iconTypeValue))
}

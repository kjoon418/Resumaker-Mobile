package com.resumaker.app.component.section

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import com.resumaker.app.component.input.PrimaryTextField

@Composable
fun TargetRoleSection(
    targetRole: String,
    onTargetRoleChange: (String) -> Unit,
    sloganFocusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    PrimaryTextField(
        value = targetRole,
        onValueChange = onTargetRoleChange,
        label = "전문 분야 및 타겟",
        placeholder = "예: 시니어 백엔드 엔지니어 / 금융권 도메인",
        modifier = modifier,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { sloganFocusRequester.requestFocus() })
    )
}

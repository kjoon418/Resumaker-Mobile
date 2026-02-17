package com.resumaker.feature.mypage.component.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.resumaker.core.ui.button.SecondaryActionButton

private val LogoutButtonGray = Color(0xFF475569)
private val WithdrawRed = Color(0xFFB91C1C)
private val WithdrawBg = Color(0xFFFEE2E2)

@Composable
fun AccountManagementSection(
    onLogout: () -> Unit,
    onWithdraw: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SecondaryActionButton(
            text = "로그아웃",
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color(0xFFF1F5F9),
            contentColor = LogoutButtonGray
        )
        SecondaryActionButton(
            text = "회원 탈퇴",
            onClick = onWithdraw,
            modifier = Modifier.fillMaxWidth(),
            containerColor = WithdrawBg,
            contentColor = WithdrawRed
        )
    }
}

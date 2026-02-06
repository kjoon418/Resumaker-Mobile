package com.resumaker.app.component.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.resumaker.app.component.button.SecondaryActionButton
import com.resumaker.app.ui.theme.ResumakerTheme

private val LogoutButtonGray = Color(0xFF475569)
private val WithdrawRed = Color(0xFFB91C1C)
private val WithdrawBg = Color(0xFFFEE2E2)

/**
 * 로그아웃 / 회원 탈퇴 버튼이 있는 계정 관리 섹션.
 * 마이페이지, 설정 화면 등에서 재사용 가능.
 */
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

@Preview(showBackground = true)
@Composable
private fun AccountManagementSectionPreview() {
    ResumakerTheme {
        AccountManagementSection(onLogout = { }, onWithdraw = { })
    }
}

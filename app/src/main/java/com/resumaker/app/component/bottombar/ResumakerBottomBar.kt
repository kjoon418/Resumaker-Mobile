package com.resumaker.app.component.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.ui.navigation.Routes
import com.resumaker.app.ui.theme.ResumakerTheme

private val PrimaryBlue = Color(0xFF2161EE)
private val InactiveGray = Color(0xFF9CA3AF)
private val TopPadding = 6.dp
private val BorderGray = Color(0xFFE5E7EB)
private val BarBackground = Color.White

/**
 * 하단 네비게이션 바. "이력서", "페르소나", "마이페이지" 3개 메뉴.
 * CareerManagerScreen(홈)에만 표시하며, 탭 시 해당 화면으로 이동.
 */
sealed class NavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    data object Resume : NavItem(Routes.AllResumes, Icons.Default.Description, "이력서")
    data object Persona : NavItem(Routes.AllPersonas, Icons.Default.Groups, "페르소나")
    data object MyPage : NavItem(Routes.MyPage, Icons.Default.Person, "마이페이지")
}

@Composable
fun ResumakerBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItem.Resume,
        NavItem.Persona,
        NavItem.MyPage
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(BarBackground)
    ) {
        HorizontalDivider(
            color = BorderGray,
            thickness = 1.dp
        )
        NavigationBar(
            modifier = Modifier
                .padding(top = TopPadding)
                .height(80.dp),
            containerColor = Color.White,
            tonalElevation = 8.dp
        ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (isSelected) PrimaryBlue else InactiveGray
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 12.sp,
                        color = if (isSelected) PrimaryBlue else InactiveGray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = PrimaryBlue,
                    selectedTextColor = PrimaryBlue,
                    unselectedIconColor = InactiveGray,
                    unselectedTextColor = InactiveGray
                )
            )
        }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResumakerBottomBarPreview() {
    ResumakerTheme {
        ResumakerBottomBar(
            currentRoute = Routes.Home,
            onNavigate = { }
        )
    }
}

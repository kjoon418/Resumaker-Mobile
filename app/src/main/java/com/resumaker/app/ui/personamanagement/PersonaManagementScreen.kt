package com.resumaker.app.ui.personamanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.component.appbar.PersonaTopBar
import com.resumaker.app.component.bottombar.ResumakerBottomBar
import com.resumaker.app.component.card.PersonaManagementCard
import com.resumaker.app.model.Persona
import com.resumaker.app.ui.navigation.Routes
import com.resumaker.app.ui.theme.ResumakerTheme

private val ScreenBackground = Color(0xFFF8FAFC)
private val FabColor = Color(0xFF2161EE)

@Composable
fun PersonaManagementScreen(
    personaList: List<Persona>,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onAddClick: () -> Unit,
    onEditClick: (Persona) -> Unit,
    onDeleteClick: (Persona) -> Unit,
    onNavigate: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            PersonaTopBar(onBackClick = onBackClick, onSearchClick = onSearchClick)
        },
        bottomBar = {
            ResumakerBottomBar(
                currentRoute = Routes.AllPersonas,
                onNavigate = onNavigate
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = FabColor,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 16.dp, end = 8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "추가", tint = Color.White)
            }
        },
        containerColor = ScreenBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "면접 및 이력서 첨삭 시 AI가 참고할 면접관의 성향을 설정하세요. 설정된 페르소나에 따라 AI의 피드백 톤앤매너가 달라집니다.",
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 160.dp)
            ) {
                items(personaList) { persona ->
                    PersonaManagementCard(
                        persona = persona,
                        onEdit = { onEditClick(persona) },
                        onDelete = { onDeleteClick(persona) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PersonaManagementScreenPreview() {
    ResumakerTheme {
        PersonaManagementScreen(
            personaList = listOf(
                Persona("1", "친절한 면접관", "많은 피드백을 주며 대화를 이끌어 줍니다.", "persona1", "2025.02.05"),
                Persona("2", "날카로운 면접관", "깊이 있는 기술 질문을 주로 합니다.", "persona2", "2025.02.03")
            ),
            onBackClick = { },
            onSearchClick = { },
            onAddClick = { },
            onEditClick = { },
            onDeleteClick = { }
        )
    }
}

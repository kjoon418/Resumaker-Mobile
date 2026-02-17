package com.resumaker.feature.careermanager.screen

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.feature.careermanager.component.appbar.PersonaTopBar
import com.resumaker.core.ui.bottombar.ResumakerBottomBar
import com.resumaker.core.ui.button.PrimaryButton
import com.resumaker.feature.careermanager.component.card.PersonaManagementCard
import com.resumaker.feature.careermanager.component.dialog.PersonaEditDialog
import com.resumaker.domain.persona.model.Persona
import com.resumaker.feature.careermanager.preview.PersonaListProvider
import com.resumaker.feature.careermanager.viewmodel.PersonaManagementViewModel
import com.resumaker.core.navigation.NavRoute
import org.koin.androidx.compose.koinViewModel

private val ScreenBackground = Color(0xFFF8FAFC)
private val FabColor = Color(0xFF2161EE)

@Composable
private fun PersonaDeleteDialog(
    persona: Persona,
    isDeleting: Boolean,
    errorMessage: String?,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("페르소나 삭제") },
        text = {
            Column {
                Text(
                    text = "\"${persona.title}\" 페르소나를 삭제하시겠습니까?",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                if (errorMessage != null) {
                    Text(
                        text = errorMessage,
                        color = Color(0xFFB91C1C),
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        },
        confirmButton = {
            PrimaryButton(
                text = if (isDeleting) "삭제 중…" else "삭제",
                onClick = onConfirm,
                enabled = !isDeleting,
                modifier = Modifier.padding(8.dp)
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소", color = Color(0xFF2161EE))
            }
        }
    )
}

@Composable
fun PersonaManagementScreen(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onAddClick: () -> Unit,
    onNavigate: (NavRoute) -> Unit = {},
    personaList: List<Persona>? = null,
    viewModel: PersonaManagementViewModel = koinViewModel()
) {
    val personasFromViewModel by viewModel.personas.collectAsState()
    val personaListToShow = personaList ?: personasFromViewModel
    val editingPersona by viewModel.editingPersona.collectAsState()
    val isUpdating by viewModel.isUpdating.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val personaToDelete by viewModel.personaToDelete.collectAsState()
    val isDeleting by viewModel.isDeleting.collectAsState()
    val currentEditing = editingPersona

    if (currentEditing != null) {
        PersonaEditDialog(
            persona = currentEditing,
            isUpdating = isUpdating,
            errorMessage = errorMessage,
            onDismiss = viewModel::cancelEdit,
            onSave = { name, description, prompt, isActive ->
                currentEditing.id.toIntOrNull()?.let { id ->
                    viewModel.updatePersona(id, name, description, prompt, isActive)
                }
            }
        )
    }

    val toDelete = personaToDelete
    if (toDelete != null) {
        PersonaDeleteDialog(
            persona = toDelete,
            isDeleting = isDeleting,
            errorMessage = errorMessage,
            onDismiss = viewModel::cancelDelete,
            onConfirm = {
                toDelete.id.toIntOrNull()?.let { id -> viewModel.deletePersona(id) }
            }
        )
    }

    Scaffold(
        topBar = {
            PersonaTopBar(onBackClick = onBackClick, onSearchClick = onSearchClick)
        },
        bottomBar = {
            ResumakerBottomBar(
                currentRoute = NavRoute.AllPersonas,
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
                items(personaListToShow) { persona ->
                    PersonaManagementCard(
                        persona = persona,
                        onEdit = { viewModel.startEdit(persona) },
                        onDelete = { viewModel.startDelete(persona) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PersonaManagementScreenPreview(
    @PreviewParameter(PersonaListProvider::class) personaList: List<Persona>
) {
    MaterialTheme {
        PersonaManagementScreen(
            personaList = personaList,
            onBackClick = { },
            onSearchClick = { },
            onAddClick = { }
        )
    }
}

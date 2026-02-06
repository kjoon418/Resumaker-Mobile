package com.resumaker.app.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.component.button.PrimaryButton
import com.resumaker.app.component.input.PrimaryTextField
import com.resumaker.app.model.Persona

@Composable
fun PersonaEditDialog(
    persona: Persona,
    isUpdating: Boolean,
    errorMessage: String?,
    onDismiss: () -> Unit,
    onSave: (name: String, description: String, prompt: String, isActive: Boolean) -> Unit
) {
    var name by remember(persona.id) { mutableStateOf(persona.title) }
    var description by remember(persona.id) { mutableStateOf(persona.description) }
    var prompt by remember(persona.id) { mutableStateOf(persona.prompt) }
    var isActive by remember(persona.id) { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("페르소나 수정") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PrimaryTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "이름",
                    placeholder = "페르소나 이름",
                    modifier = Modifier.fillMaxWidth()
                )
                PrimaryTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = "설명",
                    placeholder = "상세 설명",
                    modifier = Modifier.fillMaxWidth()
                )
                PrimaryTextField(
                    value = prompt,
                    onValueChange = { prompt = it },
                    label = "프롬프트",
                    placeholder = "AI에게 전달할 지시문",
                    modifier = Modifier.fillMaxWidth()
                )
                if (errorMessage != null) {
                    Text(
                        text = errorMessage,
                        color = Color(0xFFB91C1C),
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(checked = isActive, onCheckedChange = { isActive = it })
                        Text("활성화", fontSize = 14.sp, color = Color.DarkGray)
                    }
                }
            }
        },
        confirmButton = {
            PrimaryButton(
                text = if (isUpdating) "저장 중…" else "저장",
                onClick = { onSave(name, description, prompt, isActive) },
                enabled = !isUpdating,
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

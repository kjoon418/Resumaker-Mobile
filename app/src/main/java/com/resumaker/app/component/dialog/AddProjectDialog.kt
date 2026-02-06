package com.resumaker.app.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.resumaker.app.component.section.ResumeDetailInputPrimaryBlue
import com.resumaker.app.model.ProjectHistoryItem

@Composable
fun AddProjectDialog(
    onDismiss: () -> Unit,
    onAdd: (ProjectHistoryItem) -> Unit,
    projectTitleFocusRequester: FocusRequester
) {
    var newProjectName by remember { mutableStateOf("") }
    var newProjectKeyTasks by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LaunchedEffect(Unit) { projectTitleFocusRequester.requestFocus() }
                Text(
                    text = "프로젝트 이력 추가",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = newProjectName,
                    onValueChange = { newProjectName = it },
                    placeholder = { Text("프로젝트명", color = Color.LightGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .focusRequester(projectTitleFocusRequester),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ResumeDetailInputPrimaryBlue,
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        cursorColor = ResumeDetailInputPrimaryBlue
                    )
                )
                OutlinedTextField(
                    value = newProjectKeyTasks,
                    onValueChange = { newProjectKeyTasks = it },
                    placeholder = {
                        Text(
                            "핵심 과업 및 성과",
                            color = Color.LightGray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 80.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ResumeDetailInputPrimaryBlue,
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        cursorColor = ResumeDetailInputPrimaryBlue
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("취소", color = Color.Gray)
                    }
                    Button(
                        onClick = {
                            if (newProjectName.isNotBlank()) {
                                onAdd(
                                    ProjectHistoryItem(
                                        id = "proj-${System.currentTimeMillis()}",
                                        projectName = newProjectName.trim(),
                                        keyTasks = newProjectKeyTasks.trim()
                                    )
                                )
                                onDismiss()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ResumeDetailInputPrimaryBlue
                        )
                    ) {
                        Text("추가")
                    }
                }
            }
        }
    }
}

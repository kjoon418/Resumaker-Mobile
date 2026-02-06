package com.resumaker.app.component.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.InputChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TechStackSection(
    techStacks: List<String>,
    onRemoveTechStack: (String) -> Unit,
    onAddTechStackClick: () -> Unit,
    showAddTechStack: Boolean,
    newTechStackName: String,
    onNewTechStackNameChange: (String) -> Unit,
    onAddTechStack: () -> Unit,
    onDismissAddTechStack: () -> Unit,
    techStackFocusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            "기술 스택",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            techStacks.forEach { stack ->
                SuggestionChip(
                    onClick = { onRemoveTechStack(stack) },
                    label = { Text(stack) },
                    shape = RoundedCornerShape(20.dp)
                )
            }
            InputChip(
                selected = false,
                onClick = onAddTechStackClick,
                label = { Text("+ 기술 스택 추가") }
            )
        }
        if (showAddTechStack) {
            LaunchedEffect(Unit) { techStackFocusRequester.requestFocus() }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newTechStackName,
                    onValueChange = onNewTechStackNameChange,
                    placeholder = { Text("기술 스택 입력", color = Color.LightGray, fontSize = 14.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 48.dp)
                        .focusRequester(techStackFocusRequester),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (newTechStackName.isNotBlank()) onAddTechStack()
                        }
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ResumeDetailInputPrimaryBlue,
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        cursorColor = ResumeDetailInputPrimaryBlue
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = onAddTechStack) {
                    Text("추가", color = ResumeDetailInputPrimaryBlue)
                }
                TextButton(onClick = onDismissAddTechStack) {
                    Text("취소", color = Color.Gray)
                }
            }
        }
    }
}

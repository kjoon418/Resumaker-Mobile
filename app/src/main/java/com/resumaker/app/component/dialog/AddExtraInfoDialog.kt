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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.resumaker.app.component.section.ResumeDetailInputPrimaryBlue
import com.resumaker.app.model.ExtraInfoItem

@Composable
fun AddExtraInfoDialog(
    onDismiss: () -> Unit,
    onAdd: (ExtraInfoItem) -> Unit
) {
    var addInfoStep by remember { mutableStateOf(0) }
    var newInfoTitle by remember { mutableStateOf("") }
    var newInfoContent by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (addInfoStep == 0) "항목 제목 입력" else "항목 내용 입력",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                if (addInfoStep == 0) {
                    OutlinedTextField(
                        value = newInfoTitle,
                        onValueChange = { newInfoTitle = it },
                        placeholder = { Text("항목 제목", color = Color.LightGray) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ResumeDetailInputPrimaryBlue,
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            cursorColor = ResumeDetailInputPrimaryBlue
                        )
                    )
                } else {
                    Text(
                        text = newInfoTitle,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = newInfoContent,
                        onValueChange = { newInfoContent = it },
                        placeholder = {
                            Text(
                                "항목 내용을 입력하세요",
                                color = Color.LightGray
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ResumeDetailInputPrimaryBlue,
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            cursorColor = ResumeDetailInputPrimaryBlue
                        )
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (addInfoStep == 1) {
                        OutlinedButton(
                            onClick = {
                                addInfoStep = 0
                                newInfoContent = ""
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("이전")
                        }
                    }
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(if (addInfoStep == 1) 1f else 2f)
                    ) {
                        Text("취소", color = Color.Gray)
                    }
                    if (addInfoStep == 0) {
                        Button(
                            onClick = {
                                if (newInfoTitle.isNotBlank()) addInfoStep = 1
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ResumeDetailInputPrimaryBlue
                            )
                        ) {
                            Text("다음")
                        }
                    } else {
                        Button(
                            onClick = {
                                onAdd(
                                    ExtraInfoItem(
                                        id = "extra-${System.currentTimeMillis()}",
                                        title = newInfoTitle.trim(),
                                        content = newInfoContent.trim()
                                    )
                                )
                                onDismiss()
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
}

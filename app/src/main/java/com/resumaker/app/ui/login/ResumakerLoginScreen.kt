package com.resumaker.app.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.component.button.PrimaryButton
import com.resumaker.app.component.input.PrimaryTextField
import com.resumaker.app.component.section.LoginHeader

@Composable
fun ResumakerLoginScreen(
    onLoginSuccess: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 60.dp, bottom = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // --- 1. 헤더 섹션 ---
        LoginHeader()

        Spacer(modifier = Modifier.height(56.dp)) // 소셜 로그인이 빠져서 간격을 조금 더 넓혔습니다.

        // --- 2. 입력 섹션 ---
        PrimaryTextField(
            value = email,
            onValueChange = { email = it },
            label = "이메일",
            placeholder = "example@email.com",
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryTextField(
            value = password,
            onValueChange = { password = it },
            label = "비밀번호",
            placeholder = "비밀번호를 입력하세요",
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onLoginSuccess() }),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (passwordVisible) "비밀번호 숨기기" else "비밀번호 표시",
                        tint = Color.Gray
                    )
                }
            }
        )

        TextButton(
            onClick = { /* 비밀번호 찾기 이동 */ },
            modifier = Modifier.align(Alignment.End),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text("비밀번호를 잊으셨나요?", color = Color.Gray, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(40.dp))

        // --- 3. 로그인 버튼 ---
        PrimaryButton(
            text = "로그인",
            onClick = { onLoginSuccess() }
        )

        // 키보드 올라왔을 때도 레이아웃이 찌그러지지 않도록 스크롤 사용. weight 대신 고정 여백.
        Spacer(modifier = Modifier.height(40.dp))

        // --- 4. 하단 회원가입 안내 ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("계정이 없으신가요?", color = Color.Gray, fontSize = 14.sp)
            TextButton(
                onClick = onNavigateToSignUp,
                contentPadding = PaddingValues(start = 4.dp)
            ) {
                Text(
                    "회원가입",
                    color = Color(0xFF2161EE),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ResumakerLoginScreenPreview() {
    ResumakerLoginScreen()
}

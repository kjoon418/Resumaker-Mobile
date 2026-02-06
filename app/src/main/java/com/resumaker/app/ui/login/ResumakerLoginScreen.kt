package com.resumaker.app.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.component.button.PrimaryButton
import com.resumaker.app.component.input.PrimaryTextField
import com.resumaker.app.component.section.LoginHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun ResumakerLoginScreen(
    onLoginSuccess: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {},
    viewModel: LoginViewModel = koinViewModel()
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.loginSuccessEvent.collect { onLoginSuccess() }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 60.dp, bottom = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        LoginHeader()

        Spacer(modifier = Modifier.height(56.dp))

        PrimaryTextField(
            value = email,
            onValueChange = viewModel::updateEmail,
            label = "이메일",
            placeholder = "example@email.com",
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryTextField(
            value = password,
            onValueChange = viewModel::updatePassword,
            label = "비밀번호",
            placeholder = "비밀번호를 입력하세요",
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { viewModel.login() }),
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

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = Color(0xFFB91C1C),
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        TextButton(
            onClick = { /* 비밀번호 찾기 이동 */ },
            modifier = Modifier.align(Alignment.End),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text("비밀번호를 잊으셨나요?", color = Color.Gray, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(40.dp))

        PrimaryButton(
            text = if (isLoading) "로그인 중…" else "로그인",
            onClick = { viewModel.login() },
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(40.dp))

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

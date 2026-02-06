package com.resumaker.app.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.component.button.PrimaryButton
import com.resumaker.app.component.input.PrimaryTextField

@Composable
fun ResumakerLoginScreen(
    onLoginSuccess: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 60.dp, bottom = 24.dp)
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
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryTextField(
            value = password,
            onValueChange = { password = it },
            label = "비밀번호",
            placeholder = "비밀번호를 입력하세요",
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray) },
            trailingIcon = {
                IconButton(onClick = { /* 비밀번호 토글 로직 */ }) {
                    Icon(Icons.Default.Visibility, contentDescription = null, tint = Color.Gray)
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

        // 하단 여백을 채워주어 회원가입 문구를 맨 아래로 밀어냅니다.
        Spacer(modifier = Modifier.weight(1f))

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

@Composable
private fun LoginHeader() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color(0xFF2161EE), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("R", color = Color.White, fontWeight = FontWeight.Black, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Resumaker",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "반가워요!\n다시 시작해볼까요?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 38.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "AI와 함께 당신의 커리어를 완성하세요.",
            fontSize = 15.sp,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ResumakerLoginScreenPreview() {
    ResumakerLoginScreen()
}

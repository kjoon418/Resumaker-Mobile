package com.resumaker.app.ui.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import com.resumaker.app.component.input.TermCheckbox

@Composable
fun ResumakerSignUpScreen(
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") } // 선택 항목
    var password by remember { mutableStateOf("") }
    var isTermAgreed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()) // 입력 항목이 많아 스크롤 추가
    ) {
        // --- 1. 상단 바 ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = "뒤로가기", modifier = Modifier.size(20.dp))
            }
            Text(
                "Resumaker",
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFF2161EE),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- 2. 타이틀 섹션 ---
        Text(
            text = "나만의 커리어를\nAI와 함께 완성해보세요",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 36.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "단 몇 분 만에 전문가 수준의 이력서를 만들어 드립니다.",
            fontSize = 15.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(40.dp))

        // --- 3. 입력 필드 섹션 ---
        PrimaryTextField(
            value = name,
            onValueChange = { name = it },
            label = "이름",
            placeholder = "홍길동"
        )

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryTextField(
            value = email,
            onValueChange = { email = it },
            label = "이메일",
            placeholder = "example@resumaker.com"
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 전화번호 (선택 항목)
        PrimaryTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = "전화번호 (선택)",
            placeholder = "010-0000-0000"
        )

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryTextField(
            value = password,
            onValueChange = { password = it },
            label = "비밀번호",
            placeholder = "8자 이상 입력해주세요",
            visualTransformation = PasswordVisualTransformation(),
            trailingIcon = { Icon(Icons.Default.Visibility, contentDescription = null, tint = Color.Gray) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- 4. 약관 동의 ---
        TermCheckbox(
            checked = isTermAgreed,
            onCheckedChange = { isTermAgreed = it },
            text = "서비스 이용약관 및 개인정보 처리방침에 동의합니다. AI 생성 결과물에 대한 책임은 사용자에게 있습니다."
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- 5. 완료 버튼 ---
        PrimaryButton(
            text = "회원가입 완료",
            onClick = { /* 회원가입 로직 */ },
            // 필수 항목 입력 여부에 따른 활성화 처리 가능
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- 6. 하단 로그인 안내 ---
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("이미 계정이 있으신가요?", color = Color.Gray, fontSize = 14.sp)
            TextButton(onClick = onLoginClick) {
                Text("로그인하기", color = Color(0xFF2161EE), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResumakerSignUpScreenPreview() {
    ResumakerSignUpScreen(
        onBackClick = { },
        onLoginClick = { }
    )
}

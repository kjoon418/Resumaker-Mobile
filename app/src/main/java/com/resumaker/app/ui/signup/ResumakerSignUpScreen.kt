package com.resumaker.app.ui.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resumaker.app.component.appbar.ResumakerTopBar
import com.resumaker.app.component.button.PrimaryButton
import com.resumaker.app.component.input.PrimaryTextField
import com.resumaker.app.component.input.TermCheckbox
import com.resumaker.app.data.auth.AuthRepository
import com.resumaker.app.data.auth.RegisterParams
import com.resumaker.app.data.remote.ApiResult
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

private const val STEP_1 = 1
private const val STEP_2 = 2

private val GENDER_OPTIONS = listOf(
    "선택해주세요",
    "남성",
    "여성",
    "기타"
)

/** UI 성별 표시 → API 값 (M/F/O) */
private fun genderToApi(genderDisplay: String): String = when (genderDisplay) {
    "남성" -> "M"
    "여성" -> "F"
    "기타" -> "O"
    else -> "O"
}

@Composable
fun ResumakerSignUpScreen(
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    authRepository: AuthRepository = koinInject()
) {
    var currentStep by remember { mutableIntStateOf(STEP_1) }
    val scope = rememberCoroutineScope()

    // 1단계: 이름, 이메일, 비밀번호
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isTermAgreed by remember { mutableStateOf(false) }

    // 2단계: 나이, 성별, 직업, 연락처
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(GENDER_OPTIONS[0]) }
    var genderExpanded by remember { mutableStateOf(false) }
    var occupation by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isRegistering by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ResumakerTopBar(onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(24.dp))

        // 단계 표시 (1/2, 2/2)
        Text(
            text = "${currentStep}/2",
            fontSize = 13.sp,
            color = Color(0xFF2161EE),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        when (currentStep) {
            STEP_1 -> Step1Content(
                name = name,
                onNameChange = { name = it },
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it },
                isTermAgreed = isTermAgreed,
                onTermAgreedChange = { isTermAgreed = it },
                onNextClick = { currentStep = STEP_2 },
                onLoginClick = onLoginClick,
                canProceed = name.isNotBlank() && email.isNotBlank() && password.length >= 8 && isTermAgreed
            )

            STEP_2 -> Step2Content(
                age = age,
                onAgeChange = { age = it },
                gender = gender,
                onGenderChange = { gender = it },
                genderExpanded = genderExpanded,
                onGenderExpandedChange = { genderExpanded = it },
                occupation = occupation,
                onOccupationChange = { occupation = it },
                contact = contact,
                onContactChange = { contact = it },
                onBackClick = { currentStep = STEP_1 },
                onSubmitClick = {
                    errorMessage = null
                    scope.launch {
                        isRegistering = true
                        val params = RegisterParams(
                            username = email.trim(),
                            email = email.trim(),
                            password = password,
                            name = name.trim(),
                            age = age.toIntOrNull() ?: 0,
                            gender = genderToApi(gender),
                            job = occupation.trim(),
                            phoneNumber = contact.trim()
                        )
                        when (val result = authRepository.register(params)) {
                            is ApiResult.Success -> onLoginClick()
                            is ApiResult.Error -> errorMessage = result.message
                            is ApiResult.NetworkError -> errorMessage = "네트워크 연결을 확인해 주세요."
                        }
                        isRegistering = false
                    }
                },
                onLoginClick = onLoginClick,
                canSubmit = age.isNotBlank() && gender != GENDER_OPTIONS[0] && occupation.isNotBlank() && contact.isNotBlank(),
                errorMessage = errorMessage,
                isRegistering = isRegistering
            )
        }
    }
}

@Composable
private fun Step1Content(
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isTermAgreed: Boolean,
    onTermAgreedChange: (Boolean) -> Unit,
    onNextClick: () -> Unit,
    onLoginClick: () -> Unit,
    canProceed: Boolean
) {
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

    val focusManager = LocalFocusManager.current

    PrimaryTextField(
        value = name,
        onValueChange = onNameChange,
        label = "이름",
        placeholder = "홍길동",
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
    )

    Spacer(modifier = Modifier.height(20.dp))

    PrimaryTextField(
        value = email,
        onValueChange = onEmailChange,
        label = "이메일",
        placeholder = "example@resumaker.com",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
    )

    Spacer(modifier = Modifier.height(20.dp))

    PrimaryTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = "비밀번호",
        placeholder = "8자 이상 입력해주세요",
        visualTransformation = PasswordVisualTransformation(),
        trailingIcon = {
            Icon(
                Icons.Default.Visibility,
                contentDescription = null,
                tint = Color.Gray
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { if (canProceed) onNextClick() })
    )

    Spacer(modifier = Modifier.height(24.dp))

    TermCheckbox(
        checked = isTermAgreed,
        onCheckedChange = onTermAgreedChange,
        text = "서비스 이용약관 및 개인정보 처리방침에 동의합니다. AI 생성 결과물에 대한 책임은 사용자에게 있습니다."
    )

    Spacer(modifier = Modifier.height(32.dp))

    PrimaryButton(
        text = "다음",
        onClick = onNextClick,
        enabled = canProceed
    )

    Spacer(modifier = Modifier.height(24.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("이미 계정이 있으신가요?", color = Color.Gray, fontSize = 14.sp)
        TextButton(onClick = onLoginClick) {
            Text("로그인하기", color = Color(0xFF2161EE), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun Step2Content(
    age: String,
    onAgeChange: (String) -> Unit,
    gender: String,
    onGenderChange: (String) -> Unit,
    genderExpanded: Boolean,
    onGenderExpandedChange: (Boolean) -> Unit,
    occupation: String,
    onOccupationChange: (String) -> Unit,
    contact: String,
    onContactChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onLoginClick: () -> Unit,
    canSubmit: Boolean,
    errorMessage: String? = null,
    isRegistering: Boolean = false
) {
    Text(
        text = "추가 정보를 입력해주세요",
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 36.sp
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = "서비스 맞춤 추천을 위해 아래 정보를 입력해 주세요.",
        fontSize = 15.sp,
        color = Color.Gray
    )

    Spacer(modifier = Modifier.height(40.dp))

    val focusManager = LocalFocusManager.current

    PrimaryTextField(
        value = age,
        onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) onAgeChange(it) },
        label = "나이",
        placeholder = "만 나이를 입력해주세요",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.clearFocus()
            onGenderExpandedChange(true)
        })
    )

    Spacer(modifier = Modifier.height(20.dp))

    // 성별 드롭다운 (실험 API 미사용 버전)
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "성별",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = gender,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("선택해주세요", color = Color.Gray) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2161EE),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedLabelColor = Color(0xFF2161EE),
                    cursorColor = Color(0xFF2161EE)
                )
            )
            // 클릭을 받아 드롭다운을 열기 위한 투명 레이어 (TextField가 포커스만 가져가 클릭이 전달되지 않는 문제 방지)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { onGenderExpandedChange(!genderExpanded) }
            )

            DropdownMenu(
                expanded = genderExpanded,
                onDismissRequest = { onGenderExpandedChange(false) }
            ) {
                GENDER_OPTIONS.drop(1).forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onGenderChange(option)
                            onGenderExpandedChange(false)
                        }
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    PrimaryTextField(
        value = occupation,
        onValueChange = onOccupationChange,
        label = "직업",
        placeholder = "예: 개발자, 디자이너",
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
    )

    Spacer(modifier = Modifier.height(20.dp))

    PrimaryTextField(
        value = contact,
        onValueChange = onContactChange,
        label = "연락처",
        placeholder = "010-0000-0000",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { if (canSubmit) onSubmitClick() })
    )

    Spacer(modifier = Modifier.height(24.dp))

    if (errorMessage != null) {
        Text(
            text = errorMessage,
            color = Color(0xFFB91C1C),
            fontSize = 13.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PrimaryButton(
            text = "이전",
            onClick = onBackClick,
            modifier = Modifier.weight(1f),
            filled = false,
            enabled = !isRegistering
        )
        PrimaryButton(
            text = if (isRegistering) "가입 중…" else "회원가입 완료",
            onClick = onSubmitClick,
            modifier = Modifier.weight(1f),
            enabled = canSubmit && !isRegistering
        )
    }

    Spacer(modifier = Modifier.height(24.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("이미 계정이 있으신가요?", color = Color.Gray, fontSize = 14.sp)
        TextButton(onClick = onLoginClick) {
            Text("로그인하기", color = Color(0xFF2161EE), fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun ResumakerSignUpScreenStep1Preview() {
    val currentStep = 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ResumakerTopBar(onBackClick = { })

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "${currentStep}/2",
            fontSize = 13.sp,
            color = Color(0xFF2161EE),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Step1Content(
            name = "",
            onNameChange = { },
            email = "",
            onEmailChange = { },
            password = "",
            onPasswordChange = { },
            isTermAgreed = false,
            onTermAgreedChange = { },
            onNextClick = { },
            onLoginClick = { },
            canProceed = false
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun ResumakerSignUpScreenStep2Preview() {
    val currentStep = 2

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ResumakerTopBar(onBackClick = { })

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "${currentStep}/2",
            fontSize = 13.sp,
            color = Color(0xFF2161EE),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Step2Content(
            age = "",
            onAgeChange = { },
            gender = GENDER_OPTIONS[0],
            onGenderChange = { },
            genderExpanded = false,
            onGenderExpandedChange = { },
            occupation = "",
            onOccupationChange = { },
            contact = "",
            onContactChange = { },
            onBackClick = { },
            onSubmitClick = { },
            onLoginClick = { },
            canSubmit = false
        )
    }
}

package com.resumaker.app.ui.resumecreate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.resumaker.app.component.appbar.ResumeDetailInputTopBar
import com.resumaker.app.component.button.PrimaryButton
import com.resumaker.app.component.dialog.AddExtraInfoDialog
import com.resumaker.app.component.dialog.AddProjectDialog
import com.resumaker.app.component.progress.StepProgressBar
import com.resumaker.app.component.section.CollaborationStyleSection
import com.resumaker.app.component.section.ExtraInfoSection
import com.resumaker.app.component.section.FutureGoalsSection
import com.resumaker.app.component.section.InfoBanner
import com.resumaker.app.component.section.ProjectHistorySection
import com.resumaker.app.component.section.ResumeFormatSection
import com.resumaker.app.component.section.SloganSection
import com.resumaker.app.component.section.StrengthKeywordsSection
import com.resumaker.app.component.section.TargetRoleSection
import com.resumaker.app.component.section.TechStackSection
import com.resumaker.app.ui.theme.ResumakerTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ResumeDetailInputScreen(
    onBackClick: () -> Unit,
    onNext: () -> Unit,
    onFormatFileSelect: () -> Unit = {},
    viewModel: ResumeDetailInputViewModel = koinViewModel()
) {
    val resumeFormat by viewModel.resumeFormat.collectAsState()
    val targetRole by viewModel.targetRole.collectAsState()
    val slogan by viewModel.slogan.collectAsState()
    val strengthKeywords by viewModel.strengthKeywords.collectAsState()
    val projectHistoryItems by viewModel.projectHistoryItems.collectAsState()
    val collaborationStyle by viewModel.collaborationStyle.collectAsState()
    val techStacks by viewModel.techStacks.collectAsState()
    val futureGoals by viewModel.futureGoals.collectAsState()
    val extraItems by viewModel.extraItems.collectAsState()
    val wasPrefilledFromPdf by viewModel.wasPrefilledFromPdf.collectAsState()

    var showAddInfoDialog by remember { mutableStateOf(false) }
    var showAddProjectDialog by remember { mutableStateOf(false) }
    var newKeyword by remember { mutableStateOf("") }
    var showAddKeyword by remember { mutableStateOf(false) }
    var newTechStackName by remember { mutableStateOf("") }
    var showAddTechStack by remember { mutableStateOf(false) }

    val sloganFocusRequester = remember { FocusRequester() }
    val keywordFocusRequester = remember { FocusRequester() }
    val techStackFocusRequester = remember { FocusRequester() }
    val projectTitleFocusRequester = remember { FocusRequester() }

    if (showAddInfoDialog) {
        AddExtraInfoDialog(
            onDismiss = { showAddInfoDialog = false },
            onAdd = { item ->
                viewModel.addExtraItem(item)
                showAddInfoDialog = false
            }
        )
    }
    if (showAddProjectDialog) {
        AddProjectDialog(
            onDismiss = { showAddProjectDialog = false },
            onAdd = { item ->
                viewModel.addProjectHistoryItem(item)
                showAddProjectDialog = false
            },
            projectTitleFocusRequester = projectTitleFocusRequester
        )
    }

    Scaffold(
        topBar = {
            ResumeDetailInputTopBar(onBackClick = onBackClick)
        },
        bottomBar = {
            Column(modifier = Modifier.padding(20.dp)) {
                PrimaryButton(
                    text = "다음 단계로",
                    onClick = { viewModel.prepareForGenerate(onNext) }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            StepProgressBar(currentStep = 2, stepTitle = "정보 입력")
            Spacer(modifier = Modifier.height(24.dp))

            if (wasPrefilledFromPdf) {
                InfoBanner(
                    text = "AI 분석 완료! 기존 이력서 분석을 통해 항목을 자동으로 채웠습니다. 내용을 확인하고 필요시 수정해 주세요."
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            ResumeFormatSection(
                resumeFormat = resumeFormat,
                onResumeFormatChange = { viewModel.setResumeFormat(it) },
                onFormatFileSelect = onFormatFileSelect
            )
            Spacer(modifier = Modifier.height(24.dp))

            TargetRoleSection(
                targetRole = targetRole,
                onTargetRoleChange = { viewModel.setTargetRole(it) },
                sloganFocusRequester = sloganFocusRequester
            )
            Spacer(modifier = Modifier.height(24.dp))

            SloganSection(
                slogan = slogan,
                onSloganChange = { viewModel.setSlogan(it) },
                sloganFocusRequester = sloganFocusRequester
            )
            Spacer(modifier = Modifier.height(24.dp))

            StrengthKeywordsSection(
                strengthKeywords = strengthKeywords,
                onRemoveKeyword = { viewModel.removeStrengthKeyword(it) },
                onAddKeywordClick = { showAddKeyword = true },
                showAddKeyword = showAddKeyword,
                newKeyword = newKeyword,
                onNewKeywordChange = { newKeyword = it },
                onAddKeyword = {
                    if (newKeyword.isNotBlank()) {
                        viewModel.addStrengthKeyword(newKeyword.trim())
                        newKeyword = ""
                        showAddKeyword = false
                    }
                },
                onDismissAddKeyword = { showAddKeyword = false; newKeyword = "" },
                keywordFocusRequester = keywordFocusRequester
            )
            Spacer(modifier = Modifier.height(24.dp))

            ProjectHistorySection(
                projectHistoryItems = projectHistoryItems,
                onRemoveItem = { viewModel.removeProjectHistoryItem(it) },
                onAddClick = { showAddProjectDialog = true }
            )
            Spacer(modifier = Modifier.height(24.dp))

            CollaborationStyleSection(
                collaborationStyle = collaborationStyle,
                onCollaborationStyleChange = { viewModel.setCollaborationStyle(it) }
            )
            Spacer(modifier = Modifier.height(24.dp))

            TechStackSection(
                techStacks = techStacks,
                onRemoveTechStack = { viewModel.removeTechStack(it) },
                onAddTechStackClick = { showAddTechStack = true },
                showAddTechStack = showAddTechStack,
                newTechStackName = newTechStackName,
                onNewTechStackNameChange = { newTechStackName = it },
                onAddTechStack = {
                    if (newTechStackName.isNotBlank()) {
                        viewModel.addTechStack(newTechStackName.trim())
                        newTechStackName = ""
                        showAddTechStack = false
                    }
                },
                onDismissAddTechStack = { showAddTechStack = false; newTechStackName = "" },
                techStackFocusRequester = techStackFocusRequester
            )
            Spacer(modifier = Modifier.height(24.dp))

            FutureGoalsSection(
                futureGoals = futureGoals,
                onFutureGoalsChange = { viewModel.setFutureGoals(it) }
            )
            Spacer(modifier = Modifier.height(32.dp))

            ExtraInfoSection(
                extraItems = extraItems,
                onRemoveItem = { viewModel.removeExtraItem(it) },
                onAddClick = { showAddInfoDialog = true }
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResumeDetailInputScreenPreview() {
    ResumakerTheme {
        ResumeDetailInputScreen(
            onBackClick = { },
            onNext = { }
        )
    }
}

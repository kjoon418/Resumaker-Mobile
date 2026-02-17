# App 모듈 중재자 역할 검토

## app 모듈에 있어야 할 것 (중재자 역할)
- ✅ **Navigation**: ResumakerApp.kt (NavHost)
- ✅ **DI 합치기**: ResumakerApplication.kt (startKoin)
- ✅ **Application 클래스**: ResumakerApplication.kt
- ✅ **Main Activity**: MainActivity.kt

## 완료된 이동 (2025.02)
- CareerManagerScreen, PersonaManagementScreen, ResumeListScreen → feature:career-manager
- MyPageScreen → feature:mypage
- ResumeUploadScreen, ResumeDetailInputScreen, ResumeGeneratingScreen, ResumeCompletionScreen, ResumeEditScreen → feature:resume-builder
- ResumakerBottomBar → core:ui

## app에 남아 있는 것 (추후 이동 고려)

### 1. Screens (→ feature 모듈)
| 현재 위치 | 이동 대상 | 상태 |
|----------|----------|------|
| InterviewScreen | feature:interview (신규) | ⏳ 대기 |

### 2. ViewModels (→ feature 모듈)
| 현재 위치 | 이동 대상 |
|----------|----------|
| CareerManagerViewModel | feature:career-manager |
| MyPageViewModel | feature:mypage |
| PersonaManagementViewModel | feature:career-manager |
| ResumeUploadViewModel, ResumeDetailInputViewModel, ResumeGeneratingViewModel, ResumeCompletionViewModel | feature:resume-builder |
| ResumeEditViewModel | feature:resume-builder |

### 3. Contracts (→ feature 모듈)
| 현재 위치 | 이동 대상 |
|----------|----------|
| CareerManagerContract | feature:career-manager |
| MyPageContract | feature:mypage |
| ResumeDetailInputContract, ResumeEditContract | feature:resume-builder |

### 4. Components (→ core:ui 또는 feature)
| 현재 위치 | 이동 대상 |
|----------|----------|
| ResumakerBottomBar | core:ui |
| InterviewTopBar, InterviewBottomSection, ChatMessageBubbles, AnalyzingIndicator, InterviewerProfileHeader | feature:interview |
| PrimaryTextField | core:ui (이미 있음) - app 중복 삭제 |
| TermCheckbox | feature:login (이미 있음) - app 중복 삭제 |
| ResumePreviewCard | feature:resume-builder 또는 core:ui |

### 5. Theme/DesignSystem (→ core:designsystem)
| 현재 위치 | 이동 대상 |
|----------|----------|
| ResumakerTheme, Type.kt | core:designsystem |
| app/designsystem/* | core:designsystem (중복 제거) |

### 6. 기타
| 현재 위치 | 조치 |
|----------|------|
| app/model/* | 제거 (domain 사용) |
| app/data/remote/* | data 모듈로 |
| app/ui/preview/PreviewProviders | 각 feature로 분산 |
| PlaceholderScreen (ResumakerApp 내) | 미사용 시 제거 |

### 7. DI 모듈 (→ data/domain 모듈)
| 현재 위치 | 이동 대상 |
|----------|----------|
| networkModule | data 모듈 또는 core:network |
| repositoryModule | 각 data 모듈 |
| useCaseModule | domain 모듈 |
| viewModelModule | 제거 (각 feature 모듈로 이동) |

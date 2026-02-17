package com.resumaker.core.navigation

/**
 * Type-safe Navigation Routes.
 */
sealed interface NavRoute {
    data object Login : NavRoute
    data object SignUp : NavRoute
    data object Home : NavRoute
    data object AllResumes : NavRoute
    data object AllPersonas : NavRoute
    data object NewResume : NavRoute
    data object NewResumeStep2 : NavRoute
    data object NewResumeStep3 : NavRoute
    data object NewResumeStep4 : NavRoute
    data object ResumeEdit : NavRoute
    data object Interview : NavRoute
    data object MyPage : NavRoute
}

fun NavRoute.toRouteString(): String = when (this) {
    is NavRoute.Login -> Routes.Login
    is NavRoute.SignUp -> Routes.SignUp
    is NavRoute.Home -> Routes.Home
    is NavRoute.AllResumes -> Routes.AllResumes
    is NavRoute.AllPersonas -> Routes.AllPersonas
    is NavRoute.NewResume -> Routes.NewResume
    is NavRoute.NewResumeStep2 -> Routes.NewResumeStep2
    is NavRoute.NewResumeStep3 -> Routes.NewResumeStep3
    is NavRoute.NewResumeStep4 -> Routes.NewResumeStep4
    is NavRoute.ResumeEdit -> Routes.ResumeEdit
    is NavRoute.Interview -> Routes.Interview
    is NavRoute.MyPage -> Routes.MyPage
}

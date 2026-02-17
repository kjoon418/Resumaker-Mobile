package com.resumaker.core.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

fun NavHostController.navigateTo(route: NavRoute) {
    navigate(route.toRouteString())
}

fun NavHostController.navigateTo(route: NavRoute, builder: NavOptionsBuilder.() -> Unit) {
    navigate(route.toRouteString(), builder)
}

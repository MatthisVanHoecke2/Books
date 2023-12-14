package com.example.books.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.books.ui.screens.home.HomeScreen

@Composable
fun Navigation(navController: NavHostController, padding: PaddingValues) {
    val homeRoute = stringResource(AppPage.Home.route)

    NavHost(navController = navController, startDestination = homeRoute, modifier = Modifier.padding(padding).fillMaxSize()) {
        composable(
            route = homeRoute,
            enterTransition = { fadeInAnimation() },
            exitTransition = { fadeOutAnimation() },
        ) {
            HomeScreen()
        }
    }
}

fun fadeInAnimation(): EnterTransition {
    return fadeIn(animationSpec = tween(220))
}
fun fadeOutAnimation(): ExitTransition {
    return fadeOut(animationSpec = tween(220))
}

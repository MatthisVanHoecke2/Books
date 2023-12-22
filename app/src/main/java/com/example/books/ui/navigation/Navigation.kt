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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.books.ui.screens.bookdetails.BookDetailsScreen
import com.example.books.ui.screens.home.HomeScreen

@Composable
fun Navigation(navController: NavHostController, padding: PaddingValues) {
    val homeRoute = stringResource(AppPage.Home.route)
    val bookDetailsRoute = stringResource(AppPage.BookDetails.route)

    NavHost(navController = navController, startDestination = homeRoute, modifier = Modifier.padding(padding).fillMaxSize()) {
        composable(
            route = homeRoute,
            enterTransition = { fadeInAnimation() },
            exitTransition = { fadeOutAnimation() },
        ) {
            HomeScreen(onNavigate = { navController.navigate("home/$it") })
        }
        composable(
            route = bookDetailsRoute,
            enterTransition = { fadeInAnimation() },
            exitTransition = { fadeOutAnimation() },
            arguments = listOf(
                navArgument("key") {
                    type = NavType.StringType
                },
            ),
        ) { navBackStackEntry ->
            BookDetailsScreen(navBackStackEntry.arguments?.getString("key"))
        }
    }
}

fun fadeInAnimation(): EnterTransition {
    return fadeIn(animationSpec = tween(220))
}
fun fadeOutAnimation(): ExitTransition {
    return fadeOut(animationSpec = tween(220))
}

package com.example.books.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.example.books.ui.screens.booklistdetails.BookListDetailsScreen
import com.example.books.ui.screens.booklists.BookLists
import com.example.books.ui.screens.home.HomeScreen

@Composable
fun Navigation(navController: NavHostController, padding: PaddingValues) {
    val homeRoute = stringResource(AppPage.Home.route)
    val bookDetailsRoute = stringResource(AppPage.BookDetails.route)
    val bookListRoute = stringResource(AppPage.BookLists.route)
    val bookListDetailsRoute = stringResource(AppPage.BookListDetails.route)

    NavHost(navController = navController, startDestination = homeRoute, modifier = Modifier.padding(padding).fillMaxSize()) {
        composable(
            route = homeRoute,
            enterTransition = { fadeInAnimation() },
            exitTransition = { fadeOutAnimation() },
            popEnterTransition = { leftSlideInAnimation() },
        ) {
            HomeScreen(onNavigate = { navController.navigate("$homeRoute/$it") })
        }
        composable(
            route = bookDetailsRoute,
            enterTransition = { rightSlideInAnimation() },
            exitTransition = { fadeOutAnimation() },
            popExitTransition = { rightSlideOutAnimation() },
            arguments = listOf(
                navArgument("key") {
                    type = NavType.StringType
                },
            ),
        ) { navBackStackEntry ->
            BookDetailsScreen(navBackStackEntry.arguments?.getString("key"))
        }
        composable(
            route = bookListRoute,
            enterTransition = { fadeInAnimation() },
            exitTransition = { fadeOutAnimation() },
            popEnterTransition = { fadeInAnimation() },
        ) {
            BookLists(onNavigate = { navController.navigate("$bookListRoute/$it") })
        }
        composable(
            route = bookListDetailsRoute,
            enterTransition = { rightSlideInAnimation() },
            exitTransition = { fadeOutAnimation() },
            popEnterTransition = { leftSlideInAnimation() },
            popExitTransition = { rightSlideOutAnimation() },
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                },
            ),
        ) { navBackStackEntry ->
            val id: Long? = navBackStackEntry.arguments?.getLong("id")
            if (id != null) {
                BookListDetailsScreen(id = id, onNavigate = { navController.navigate("$homeRoute/$it") })
            }
        }
    }
}

fun fadeInAnimation(): EnterTransition {
    return fadeIn(animationSpec = tween(220))
}
fun fadeOutAnimation(): ExitTransition {
    return fadeOut(animationSpec = tween(220))
}

fun rightSlideInAnimation(): EnterTransition {
    return slideInHorizontally(animationSpec = tween(220)) { it } + fadeIn(animationSpec = tween(220))
}

fun leftSlideInAnimation(): EnterTransition {
    return slideInHorizontally(animationSpec = tween(220)) + fadeIn(animationSpec = tween(220))
}
fun rightSlideOutAnimation(): ExitTransition {
    return slideOutHorizontally(animationSpec = tween(220)) { it } + fadeOut(animationSpec = tween(220))
}

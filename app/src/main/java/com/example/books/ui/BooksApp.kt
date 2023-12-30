package com.example.books.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.books.R
import com.example.books.ui.navigation.AppPage
import com.example.books.ui.navigation.Navigation
import com.example.books.ui.navigation.components.DrawerContent
import com.example.books.ui.navigation.components.TopBar
import kotlinx.coroutines.launch

/**
 * Main composable of the Books app
 * @param navController the controller used for navigation
 * */
@Composable
fun BooksApp(navController: NavHostController = rememberNavController()) {
    Navigation(navController = navController, padding = PaddingValues())

    val extraPadding = dimensionResource(R.dimen.padding_medium)

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentPage = AppPage.entries.find {
        backStackEntry?.destination?.route == stringResource(it.route)
    } ?: AppPage.Home

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = NavShape(0.dp, 0.7f),
                drawerContainerColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                val currentRoute = stringResource(currentPage.route)
                DrawerContent(onClick = {
                    navController.navigate(it) {
                        launchSingleTop = true
                        popUpTo(currentRoute) {
                            inclusive = true
                        }
                    }
                    scope.launch {
                        drawerState.apply {
                            close()
                        }
                    }
                })
            }
        },
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    onIconClick = {
                        if (navController.previousBackStackEntry != null) {
                            navController.navigateUp()
                        } else {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }
                    },
                    title = stringResource(currentPage.displayName),
                    icon = if (navController.previousBackStackEntry != null) Icons.Default.ArrowBack else Icons.Default.Menu, // set icon based on functionality
                )
            },
        ) {
            Navigation(
                navController,
                PaddingValues(
                    start = it.calculateLeftPadding(LayoutDirection.Ltr) + extraPadding,
                    end = it.calculateRightPadding(LayoutDirection.Rtl) + extraPadding,
                    top = it.calculateTopPadding() + extraPadding,
                    bottom = it.calculateBottomPadding() + extraPadding,
                ),
            )
        }
    }
}

class NavShape(
    private val widthOffset: Dp,
    private val scale: Float,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        return Outline.Rectangle(
            Rect(
                Offset.Zero,
                Offset(
                    size.width * scale + with(density) { widthOffset.toPx() },
                    size.height,
                ),
            ),
        )
    }
}

package com.example.books.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.books.R
import com.example.books.ui.navigation.AppPage
import com.example.books.ui.navigation.Navigation
import com.example.books.ui.navigation.components.DrawerContent
import com.example.books.ui.navigation.components.TopBar
import kotlinx.coroutines.launch

@Composable
fun BooksApp(navController: NavHostController = rememberNavController()) {
    Navigation(navController = navController, padding = PaddingValues())

    val extraPadding = dimensionResource(R.dimen.padding_medium)

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentPage = AppPage.values().find {
        backStackEntry?.destination?.route == stringResource(it.route)
    } ?: AppPage.Home

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = MaterialTheme.shapes.extraSmall,
                drawerContainerColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.fillMaxWidth(0.7f),
            ) {
                DrawerContent(onClick = {
                    navController.navigate(it)
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
                        if (currentPage.canBack) {
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
                    icon = if (currentPage.canBack) Icons.Default.ArrowBack else Icons.Default.Menu,
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

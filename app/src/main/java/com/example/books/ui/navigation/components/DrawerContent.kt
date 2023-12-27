package com.example.books.ui.navigation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.books.ui.navigation.AppPage

@Composable
fun DrawerContent(onClick: (String) -> Unit) {
    val homeRoute = stringResource(AppPage.Home.route)
    val listRoute = stringResource(AppPage.BookLists.route)
    Column {
        DrawerItem(
            icon = Icons.Default.Home,
            label = stringResource(AppPage.Home.displayName),
            onClick = { onClick.invoke(homeRoute) },
        )
        DrawerItem(
            icon = Icons.Default.LibraryBooks,
            label = stringResource(AppPage.BookLists.displayName),
            onClick = { onClick.invoke(listRoute) },
        )
    }
}

package com.example.books.ui.navigation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable

@Composable
fun DrawerContent(onClick: (String) -> Unit) {
    Column {
        DrawerItem(
            icon = Icons.Default.Home,
            label = "Home",
            onClick = { onClick.invoke("home") },
        )
    }
}

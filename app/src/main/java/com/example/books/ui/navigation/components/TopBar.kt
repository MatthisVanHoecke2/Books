package com.example.books.ui.navigation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.books.R

/**
 * The top bar of the application
 * @param onIconClick callback function for clicking on the menu drawer icon
 * @param title text to display in the top bar
 * @param icon icon inside the menu drawer button
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onIconClick: () -> Unit,
    title: String = stringResource(R.string.app_name),
    icon: ImageVector,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onIconClick.invoke() }) {
                Icon(icon, contentDescription = "toggle drawer")
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    )
}

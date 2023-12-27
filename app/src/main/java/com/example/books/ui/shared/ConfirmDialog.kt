package com.example.books.ui.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * Custom composable confirm dialog
 * @param onDismiss callback for closing the dialog
 * @param onConfirm callback for confirming
 * @param title composable title
 * @param text composable content
 * */
@Composable
fun ConfirmDialog(
    icon: @Composable() () -> Unit = { Icon(Icons.Default.Info, contentDescription = "info icon") },
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmButton: @Composable() () -> Unit = {
        Button(onClick = { onConfirm.invoke() }) {
            Text("Confirm")
        }
    },
    dismissButton: @Composable() () -> Unit = {
        Button(onClick = { onDismiss.invoke() }) {
            Text("Cancel")
        }
    },
    title: @Composable() () -> Unit,
    text: @Composable() () -> Unit,
) {
    AlertDialog(
        icon = { icon.invoke() },
        title = title,
        text = text,
        onDismissRequest = { onDismiss.invoke() },
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        titleContentColor = MaterialTheme.colorScheme.onBackground,
    )
}

package com.example.books.ui.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * Composable for displaying data efficiently
 * @param caption caption text
 * @param content content component
 * */
@Composable
fun DetailComponent(
    caption: String,
    content: @Composable() () -> Unit,
) {
    Column {
        Text(caption, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
        content.invoke()
    }
}

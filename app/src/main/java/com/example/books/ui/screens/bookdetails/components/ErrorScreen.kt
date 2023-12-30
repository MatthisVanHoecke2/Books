package com.example.books.ui.screens.bookdetails.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * Screen to display when the repository request fails
 * @param message error message
 * */
@Composable
fun ErrorScreen(message: String) {
    Text(text = message)
}

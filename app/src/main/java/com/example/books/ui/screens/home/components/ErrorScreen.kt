package com.example.books.ui.screens.home.components

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Text

/**
 * Screen to display when the repository request fails
 * @param message error message
 * */
fun LazyGridScope.errorScreen(message: String) {
    item(span = { GridItemSpan(maxLineSpan) }) {
        Text(message)
    }
}

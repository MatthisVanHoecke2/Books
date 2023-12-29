package com.example.books.ui.screens.home.components

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Text

fun LazyGridScope.errorScreen(message: String) {
    item(span = { GridItemSpan(maxLineSpan) }) {
        Text(message)
    }
}
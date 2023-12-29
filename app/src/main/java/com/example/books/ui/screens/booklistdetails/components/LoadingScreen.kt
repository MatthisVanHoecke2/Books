package com.example.books.ui.screens.booklistdetails.components

import androidx.compose.foundation.lazy.LazyListScope
import com.example.books.ui.shared.CenteredSpinner

fun LazyListScope.loadingScreen() {
    item {
        CenteredSpinner()
    }
}

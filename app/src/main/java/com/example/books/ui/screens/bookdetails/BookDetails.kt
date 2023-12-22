package com.example.books.ui.screens.bookdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun BookDetailsScreen(key: String?) {
    val viewModel: BookDetailsVM = viewModel(factory = BookDetailsVMFactory(key.toString()))
    val bookDetailsUiState by viewModel.bookDetailsUiState.collectAsState()
    val book = bookDetailsUiState.book
    val imageUrl = "https://covers.openlibrary.org/b/id/${book?.covers?.first()}-L.jpg"
    val loading by viewModel.loading.collectAsState()
    if (!loading) {
        Column {
            DetailText(caption = "Title", text = { Text("${book?.title}") })
            AsyncImage(
                model = imageUrl,
                contentDescription = book?.title,
            )
        }
    } else {
        Row(horizontalArrangement = Arrangement.Center) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}

@Composable
fun DetailText(
    caption: String,
    text: @Composable() () -> Unit,
) {
    Column {
        Text(caption, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
        text.invoke()
    }
}

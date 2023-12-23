package com.example.books.ui.screens.bookdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.example.books.R
import com.example.books.network.data.books.BookDetail

@Composable
fun BookDetailsScreen(key: String?) {
    val viewModel: BookDetailsVM = viewModel(factory = BookDetailsVM.Companion.Factory(key.toString()))
    val bookDetailsUiState by viewModel.bookDetailsUiState.collectAsState()
    val book = bookDetailsUiState.book
    val ratings = bookDetailsUiState.ratings
    val loading by viewModel.loading.collectAsState()
    if (!loading) {
        Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))) {
            DetailText(caption = "Title", text = { Text("${book?.title}") })
            DetailText(caption = "Ratings", text = { Text("${String.format("%.1f", ratings)}/5") })
            if (book is BookDetail) {
                val imageUrl = "https://covers.openlibrary.org/b/id/${book.covers.first()}-L.jpg"
                Box(
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.cover_width))
                        .height(dimensionResource(R.dimen.cover_height)),
                ) {
                    SubcomposeAsyncImage(
                        model = imageUrl,
                        contentDescription = book.title,
                        loading = { Text(book.title.toString()) },
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
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

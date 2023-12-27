package com.example.books.ui.screens.bookdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.example.books.R
import com.example.books.model.Book
import com.example.books.network.data.books.BookDetail
import com.example.books.ui.shared.DetailComponent

@Composable
fun BookDetailsScreen(key: String?) {
    val viewModel: BookDetailsVM = viewModel(factory = BookDetailsVM.Companion.Factory(key.toString()))
    when (val apiState = viewModel.bookApiState) {
        is BookApiState.Loading -> LoadingScreen()
        is BookApiState.Success -> SuccessScreen(book = apiState.book, ratings = apiState.rating)
        is BookApiState.Error -> ErrorScreen()
    }
}

@Composable
fun LoadingScreen() {
    Row(horizontalArrangement = Arrangement.Center) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
fun SuccessScreen(book: Book, ratings: Double) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = Modifier.verticalScroll(
            rememberScrollState(),
        ),
    ) {
        DetailComponent(caption = "Title", content = { Text(book.title) })
        DetailComponent(caption = "Ratings", content = { Text("${String.format("%.1f", ratings)}/5") })
        if (book is BookDetail && book.covers.isNotEmpty()) {
            val imageUrl = "https://covers.openlibrary.org/b/id/${book.covers.first()}-L.jpg"
            Box(
                modifier = Modifier
                    .width(dimensionResource(R.dimen.cover_width))
                    .height(dimensionResource(R.dimen.cover_height)),
            ) {
                SubcomposeAsyncImage(
                    model = imageUrl,
                    contentDescription = book.title,
                    loading = { Text(book.title) },
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
fun ErrorScreen() {
    Text(text = "An error occurred while fetching the book details")
}

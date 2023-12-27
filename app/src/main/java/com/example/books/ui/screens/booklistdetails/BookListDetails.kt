package com.example.books.ui.screens.booklistdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.example.books.R
import com.example.books.model.Book

@Composable
fun BookListDetailsScreen(id: Long, onNavigate: (String) -> Unit) {
    val viewModel = viewModel<BookListDetailsViewModel>(factory = BookListDetailsViewModel.Companion.Factory(id))
    val bookListDetailsUiState by viewModel.bookListUiState.collectAsState()
    val result = bookListDetailsUiState.bookList
    val apiState = viewModel.bookListApiState

    Column {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
        ) {
            when (apiState) {
                is BookListApiState.Loading -> loadingScreen()
                is BookListApiState.Success -> successScreen(searchResult = apiState.bookList, onNavigate = { onNavigate.invoke(it) })
                is BookListApiState.Error -> errorScreen()
            }
        }
    }
}

fun LazyGridScope.errorScreen() {
    item(span = { GridItemSpan(maxLineSpan) }) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
        ) {
            Text("An error occurred while fetching data")
        }
    }
}

fun LazyGridScope.loadingScreen() {
    item(span = { GridItemSpan(maxLineSpan) }) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}

fun LazyGridScope.successScreen(searchResult: List<Book>, onNavigate: (String) -> Unit) {
    items(
        searchResult,
    ) { book ->
        Row(
            modifier = Modifier.clickable {
                val key = book.key
                onNavigate.invoke(key)
            }.padding(dimensionResource(R.dimen.padding_small)),
            horizontalArrangement = Arrangement.Center,
        ) {
            if (book.coverId != null) {
                val imageUrl = "https://covers.openlibrary.org/b/id/${book.coverId}-L.jpg"

                SubcomposeAsyncImage(
                    model = imageUrl,
                    contentDescription = book.title,
                    loading = { Text(book.title) },
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                Text(book.title)
            }
        }
    }
}

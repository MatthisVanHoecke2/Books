package com.example.books.ui.screens.booklistdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.example.books.R
import com.example.books.model.Book
import com.example.books.ui.screens.booklistdetails.model.BookListDetailsViewModel
import com.example.books.ui.screens.booklistdetails.model.BookListGetApiState
import com.example.books.ui.shared.ConfirmDialog

@Composable
fun BookListDetailsScreen(id: Long, onNavigate: (String) -> Unit) {
    val viewModel = viewModel<BookListDetailsViewModel>(factory = BookListDetailsViewModel.Companion.Factory(id))
    val apiState = viewModel.bookListApiState

    Column {
        LazyColumn {
            when (apiState) {
                is BookListGetApiState.Loading -> loadingScreen()
                is BookListGetApiState.Success -> successScreen(
                    searchResult = apiState.bookList,
                    onNavigate = { onNavigate.invoke(it) },
                    onDelete = { viewModel.deleteBook(it) },
                )
                is BookListGetApiState.Error -> errorScreen()
            }
        }
    }
}

fun LazyListScope.errorScreen() {
    item {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
        ) {
            Text("An error occurred while fetching the book lists")
        }
    }
}

fun LazyListScope.loadingScreen() {
    item {
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

fun LazyListScope.successScreen(searchResult: List<Book>, onNavigate: (String) -> Unit, onDelete: (String) -> Unit) {
    itemsIndexed(
        searchResult,
    ) { index, book ->
        var openConfirmDelete by remember { mutableStateOf(false) }

        if (openConfirmDelete) {
            ConfirmDialog(
                onDismiss = { openConfirmDelete = false },
                onConfirm = {
                    openConfirmDelete = false
                    onDelete.invoke(book.key)
                },
                title = { Text("Confirm", style = MaterialTheme.typography.titleLarge) },
            ) {
                Text("Are you sure you want to delete this book?")
            }
        }

        ListItem(
            leadingContent = {
                if (book.coverId != null) {
                    val imageUrl = "https://covers.openlibrary.org/b/id/${book.coverId}-M.jpg"

                    SubcomposeAsyncImage(
                        model = imageUrl,
                        contentDescription = book.title,
                        loading = {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        },
                        contentScale = ContentScale.FillWidth,
                    )
                }
            },
            trailingContent = {
                IconButton(
                    onClick = { openConfirmDelete = true },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.error,
                    ),
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Delete item")
                }
            },
            headlineContent = { Text(book.title) },
            modifier = Modifier.clickable {
                val key = book.key
                onNavigate.invoke(key)
            },
        )
        if (index < searchResult.lastIndex) {
            Divider(color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f))
        }
    }
}

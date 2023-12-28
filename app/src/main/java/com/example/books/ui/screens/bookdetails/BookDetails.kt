package com.example.books.ui.screens.bookdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.example.books.R
import com.example.books.model.Book
import com.example.books.network.data.books.BookDetail
import com.example.books.persistence.data.booklists.BookList
import com.example.books.ui.screens.bookdetails.model.BookDetailsVM
import com.example.books.ui.screens.bookdetails.model.BookGetApiState
import com.example.books.ui.screens.bookdetails.model.BookInsertApiState
import com.example.books.ui.shared.ConfirmDialog
import com.example.books.ui.shared.CustomAlertDialog
import com.example.books.ui.shared.DetailComponent

@Composable
fun BookDetailsScreen(key: String?) {
    val viewModel: BookDetailsVM = viewModel(factory = BookDetailsVM.Companion.Factory(key.toString()))
    when (val apiState = viewModel.bookGetApiState) {
        is BookGetApiState.Loading -> LoadingScreen()
        is BookGetApiState.Success -> SuccessScreen(
            book = apiState.book,
            ratings = apiState.rating,
            bookLists = apiState.bookLists,
            insertBookIntoList = {
                viewModel.insertIntoList(bookList = it, book = apiState.book, rating = apiState.rating)
            },
            apiState = viewModel.bookInsertApiState,
            closeAlert = { viewModel.closeAlertDialog() },
        )
        is BookGetApiState.Error -> ErrorScreen()
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
fun SuccessScreen(book: Book, ratings: Double, bookLists: List<BookList>, insertBookIntoList: (BookList) -> Unit, apiState: BookInsertApiState, closeAlert: () -> Unit) {
    var openDialog by remember { mutableStateOf(false) }

    when (apiState) {
        is BookInsertApiState.Start -> {}
        is BookInsertApiState.Success -> {
            CustomAlertDialog(
                title = { Text("Success", style = MaterialTheme.typography.titleLarge) },
                text = { Text(apiState.message) },
                onOk = { closeAlert.invoke() },
            )
        }
        is BookInsertApiState.Error -> {
            CustomAlertDialog(
                title = { Text("Error", style = MaterialTheme.typography.titleLarge) },
                text = { Text(apiState.message) },
                onOk = { closeAlert.invoke() },
            )
        }
    }

    if (openDialog) {
        ConfirmDialog(
            icon = { },
            onDismiss = { openDialog = false },
            onConfirm = { openDialog = false },
            title = { Text("Choose list", style = MaterialTheme.typography.titleLarge) },
            dismissButton = { },
            confirmButton = { },
            modifier = Modifier.heightIn(0.dp, 200.dp),
        ) {
            LazyColumn {
                items(bookLists) {
                    ListItem(
                        headlineContent = { Text(it.listName) },
                        modifier = Modifier.clickable {
                            insertBookIntoList.invoke(it)
                            openDialog = false
                        },
                    )
                }
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = Modifier.verticalScroll(
            rememberScrollState(),
        ),
    ) {
        DetailComponent(caption = "Title", content = { Text(book.title) })
        DetailComponent(caption = "Ratings", content = { Text("${String.format("%.1f", ratings)}/5") })
        if (book.coverId != null || (book is BookDetail && book.covers.isNotEmpty())) {
            val cover = if (book.coverId != null) book.coverId else (book as BookDetail).covers.first()
            val imageUrl = "https://covers.openlibrary.org/b/id/$cover-L.jpg"
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
        Button(onClick = { openDialog = true }) {
            Icon(Icons.Default.Add, contentDescription = "Add icon")
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text("Add to list")
        }
    }
}

@Composable
fun ErrorScreen() {
    Text(text = "An error occurred while fetching the book details")
}

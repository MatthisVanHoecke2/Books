package com.example.books.ui.screens.booklistdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.books.model.Book
import com.example.books.ui.screens.booklistdetails.components.errorScreen
import com.example.books.ui.screens.booklistdetails.components.loadingScreen
import com.example.books.ui.screens.booklistdetails.components.successScreen
import com.example.books.ui.screens.booklistdetails.model.BookListDetailsViewModel
import com.example.books.ui.screens.booklistdetails.model.BookListGetApiState
import com.example.books.ui.shared.ConfirmDialog

@Composable
fun BookListDetailsScreen(id: Long, onNavigate: (String) -> Unit) {
    val viewModel = viewModel<BookListDetailsViewModel>(factory = BookListDetailsViewModel.Companion.Factory(id))
    val apiState = viewModel.bookListApiState
    var openConfirmDelete by remember { mutableStateOf(false) }
    var bookToDelete: Book? by remember { mutableStateOf(null) }

    if (openConfirmDelete) {
        ConfirmDialog(
            onDismiss = { openConfirmDelete = false },
            onConfirm = {
                openConfirmDelete = false
                viewModel.deleteBook(bookToDelete!!.key)
            },
            title = { Text("Confirm", style = MaterialTheme.typography.titleLarge) },
        ) {
            Text("Are you sure you want to delete book '${bookToDelete!!.title}'?")
        }
    }

    Column {
        LazyColumn {
            when (apiState) {
                is BookListGetApiState.Loading -> loadingScreen()
                is BookListGetApiState.Success -> successScreen(
                    searchResult = apiState.bookList,
                    onNavigate = { onNavigate.invoke(it) },
                    onDelete = {
                        bookToDelete = it
                        openConfirmDelete = true
                    },
                )
                is BookListGetApiState.Error -> errorScreen(apiState.message)
            }
        }
    }
}

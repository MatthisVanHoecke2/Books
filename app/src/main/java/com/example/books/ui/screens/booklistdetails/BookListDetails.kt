package com.example.books.ui.screens.booklistdetails

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.books.model.Book
import com.example.books.ui.screens.booklistdetails.components.ErrorScreen
import com.example.books.ui.screens.booklistdetails.components.LoadingScreen
import com.example.books.ui.screens.booklistdetails.components.SuccessScreen
import com.example.books.ui.screens.booklistdetails.model.BookListApiState
import com.example.books.ui.screens.booklistdetails.model.BookListDetailsViewModel
import com.example.books.ui.shared.ConfirmDialog

/**
 * Composable screen for displaying the BookListDetails page
 * @param id the primary key to retrieve the specific book list
 * @param onNavigate callback function for navigating to a book detail page
 * */
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

    when (apiState) {
        is BookListApiState.Loading -> LoadingScreen()
        is BookListApiState.Success -> SuccessScreen(
            bookList = apiState.bookList,
            bookListItems = apiState.listOfBooks,
            onNavigate = { onNavigate.invoke(it) },
            onDelete = {
                bookToDelete = it
                openConfirmDelete = true
            },
        )
        is BookListApiState.Error -> ErrorScreen(apiState.message)
    }
}

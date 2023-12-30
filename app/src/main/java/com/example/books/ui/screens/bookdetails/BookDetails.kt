package com.example.books.ui.screens.bookdetails

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.books.ui.screens.bookdetails.components.ErrorScreen
import com.example.books.ui.screens.bookdetails.components.LoadingScreen
import com.example.books.ui.screens.bookdetails.components.SuccessScreen
import com.example.books.ui.screens.bookdetails.model.BookDetailsViewModel
import com.example.books.ui.screens.bookdetails.model.BookGetApiState

/**
 * Composable screen for displaying the BookDetails page
 * @param key the primary key to retrieve the specific book
 * */
@Composable
fun BookDetailsScreen(key: String?) {
    val viewModel: BookDetailsViewModel = viewModel(factory = BookDetailsViewModel.Companion.Factory(key.toString()))
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
        is BookGetApiState.Error -> ErrorScreen(apiState.message)
    }
}

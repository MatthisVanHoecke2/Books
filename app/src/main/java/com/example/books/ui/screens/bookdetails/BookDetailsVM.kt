package com.example.books.ui.screens.bookdetails

import androidx.lifecycle.viewModelScope
import com.example.books.network.BooksApi
import com.example.books.ui.CustomViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel class for BookDetails page
 *
 * @property key the key value to retrieve the book details from the API
 * */
class BookDetailsVM(private val key: String) : CustomViewModel() {
    private val _bookDetailsUiState = MutableStateFlow(BookDetailsUiState())
    val bookDetailsUiState = _bookDetailsUiState.asStateFlow()

    init {
        onLoadChange(true)
        val retrofit = BooksApi.retrofitService
        viewModelScope.launch {
            val book = async { retrofit.getBook(key) }
            val ratings = async { retrofit.getRatings(key) }
            _bookDetailsUiState.update { it.copy(book = book.await(), ratings = ratings.await()) }
            onLoadChange(false)
        }
    }
}

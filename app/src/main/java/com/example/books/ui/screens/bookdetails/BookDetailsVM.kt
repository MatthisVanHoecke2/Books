package com.example.books.ui.screens.bookdetails

import androidx.lifecycle.viewModelScope
import com.example.books.network.BooksApi
import com.example.books.ui.CustomViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailsVM(private val key: String) : CustomViewModel() {
    private val _bookDetailsUiState = MutableStateFlow(BookDetailsUiState())
    val bookDetailsUiState = _bookDetailsUiState.asStateFlow()

    init {
        onLoadChange(true)
        viewModelScope.launch {
            val retrofit = BooksApi.retrofitService
            val book = retrofit.getBook(key)
            val ratings = retrofit.getRatings(key)
            _bookDetailsUiState.update { it.copy(book = book, ratings = ratings) }
            onLoadChange(false)
        }
    }
}

package com.example.books.ui.screens.home.model

import com.example.books.model.Book

data class HomeUiState(
    val search: String = "",
    val searchResult: List<Book> = emptyList(),
    val currentPage: Int = 0,
    val endOfList: Boolean = false,
)

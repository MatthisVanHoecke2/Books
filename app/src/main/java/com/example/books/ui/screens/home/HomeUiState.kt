package com.example.books.ui.screens.home

import com.example.books.model.Book

data class HomeUiState(
    val search: String = "",
    val searchResult: List<Book> = emptyList(),
    val currentPage: Int = 0,
)

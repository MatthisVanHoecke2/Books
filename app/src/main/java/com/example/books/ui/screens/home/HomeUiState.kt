package com.example.books.ui.screens.home

import com.example.books.data.books.BookIndex

data class HomeUiState(
    val search: String = "",
    val searchResult: List<BookIndex> = emptyList()
)

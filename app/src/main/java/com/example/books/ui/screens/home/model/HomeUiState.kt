package com.example.books.ui.screens.home.model

import com.example.books.model.Book

/**
 * Data class for holding Home page UI values
 * @property search textfield value for searching the repository for books
 * @property searchResult result list of books from the repository
 * @property currentPage the current page of the result list
 * @property endOfList value to determine whether we have reached the end of the search result
 * */
data class HomeUiState(
    val search: String = "",
    val searchResult: List<Book> = emptyList(),
    val currentPage: Int = 0,
    val endOfList: Boolean = false,
)

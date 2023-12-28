package com.example.books.ui.screens.booklists.model

import com.example.books.persistence.data.booklists.BookList

data class BookListUiState(
    val openCreateDialog: Boolean = false,
    val createDialogText: String = "",
    val bookLists: List<BookList> = emptyList(),
)

package com.example.books.ui.screens.booklists.model

import com.example.books.persistence.data.booklists.BookList

/**
 * Data class for holding the BookList page UI variables
 * @property openCreateDialog value for opening the create dialog
 * @property createDialogText text value of the create dialog textfield
 * @property bookLists list of [BookList]
 * */
data class BookListUiState(
    val openCreateDialog: Boolean = false,
    val createDialogText: String = "",
    val bookLists: List<BookList> = emptyList(),
)

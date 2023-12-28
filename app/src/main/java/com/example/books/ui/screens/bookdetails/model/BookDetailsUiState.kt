package com.example.books.ui.screens.bookdetails.model

import com.example.books.model.Book
import com.example.books.persistence.data.booklists.BookList

data class BookDetailsUiState(val book: Book? = null, val ratings: Double? = 0.0, val bookLists: List<BookList> = emptyList())

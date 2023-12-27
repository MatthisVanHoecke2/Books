package com.example.books.ui.screens.bookdetails

import com.example.books.persistence.data.booklists.BookList

data class BookDetailsApiState(val bookLists: List<BookList> = emptyList())

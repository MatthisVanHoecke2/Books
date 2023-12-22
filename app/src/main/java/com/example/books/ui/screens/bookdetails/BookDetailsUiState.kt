package com.example.books.ui.screens.bookdetails

import com.example.books.data.books.BookDetail
import com.example.books.data.books.BookRatings

data class BookDetailsUiState(val book: BookDetail? = null, val ratings: BookRatings? = BookRatings())

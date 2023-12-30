package com.example.books.ui.screens.booklistdetails.model

import com.example.books.model.Book
import com.example.books.persistence.data.booklists.BookList

/**
 * Data class for holding the BookListDetail page UI variables
 * @property bookList the current selected book list
 * @property listOfBooks a list of books that are part of the book list
 * */
data class BookListDetailsUiState(val bookList: BookList? = null, val listOfBooks: List<Book> = emptyList())

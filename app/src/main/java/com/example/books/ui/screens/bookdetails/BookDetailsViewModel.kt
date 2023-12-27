package com.example.books.ui.screens.bookdetails

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.books.BooksApplication
import com.example.books.model.Book
import com.example.books.network.data.books.BookDetail
import com.example.books.persistence.data.booklists.BookList
import com.example.books.persistence.data.books.BookEntity
import com.example.books.repository.BookListsRepository
import com.example.books.repository.BookRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface BookApiState {
    data class Success(val book: Book, val rating: Double, val bookLists: List<BookList>) : BookApiState
    data object Loading : BookApiState
    data object Error : BookApiState
}

/**
 * ViewModel class for BookDetails page
 *
 * @property key the key value to retrieve the book details from the API
 * */
class BookDetailsVM(private val booksRepository: BookRepository, private val bookListsRepository: BookListsRepository, private val key: String) : ViewModel() {
    var bookApiState: BookApiState by mutableStateOf(BookApiState.Loading)
        private set

    private val _bookDetailsApiState = MutableStateFlow(BookDetailsUiState())

    init {
        viewModelScope.launch {
            bookApiState = try {
                val bookRequest = async { booksRepository.getBook(key) }
                val ratingsRequest = async { booksRepository.getRatings(key) }
                val bookListsRequest = async { bookListsRepository.getLists() }
                val book = bookRequest.await()
                val ratings = ratingsRequest.await()
                val bookLists = bookListsRequest.await()
                _bookDetailsApiState.update { it.copy(bookLists = bookLists) }
                BookApiState.Success(book, ratings, bookLists)
            } catch (ex: Exception) {
                BookApiState.Error
            }
        }
    }

    fun insertIntoList(bookList: BookList, book: Book, rating: Double) {
        val bookEntity: BookEntity = if (book is BookDetail) {
            BookEntity(
                key = book.key,
                title = book.title,
                publishYear = book.publishYear,
                rating = rating,
                coverId = book.covers.first(),
            )
        } else {
            book as BookEntity
        }

        viewModelScope.launch {
            try {
                bookListsRepository.insertIntoList(bookList, bookEntity)
                BookApiState.Success(book, rating, _bookDetailsApiState.value.bookLists)
            } catch (ex: IOException) {
                BookApiState.Error
            } catch (ex: SQLiteConstraintException) {
                BookApiState.Error
            }
        }
    }

    companion object {
        class Factory(private val key: String) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = (extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BooksApplication)
                val booksRepository = application.container.booksRepository
                val bookListsRepository = application.container.bookListsRepository
                return modelClass.getConstructor(BookRepository::class.java, BookListsRepository::class.java, String::class.java).newInstance(booksRepository, bookListsRepository, key)
            }
        }
    }
}

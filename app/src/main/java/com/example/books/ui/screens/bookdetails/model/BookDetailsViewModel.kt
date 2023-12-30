package com.example.books.ui.screens.bookdetails.model

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
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Interface for determining the current state of a book GET operation
 * */
sealed interface BookGetApiState {
    /**
     * Data class for when the operation was successful
     * @param book the [Book] that was retrieved from the [BookRepository]
     * @param rating the rating of the [Book] that was retrieved from the [BookRepository]
     * @param bookLists the list of [BookList] that was retrieved from the [BookListsRepository]
     * */
    data class Success(val book: Book, val rating: Double, val bookLists: List<BookList>) : BookGetApiState

    /**
     * Data object for when the operation is loading
     * */
    data object Loading : BookGetApiState

    /**
     * Data class for when the operation has failed
     * @param message error message to display
     * */
    data class Error(val message: String) : BookGetApiState
}

/**
 * Interface for determining the current state of a book INSERT operation
 * */
sealed interface BookInsertApiState {

    /**
     * Data object for when the operation is waiting to be executed
     * */
    data object Start : BookInsertApiState

    /**
     * Data class for when the operation was successful
     * @param message success message to display
     * */
    data class Success(val message: String) : BookInsertApiState

    /**
     * Data class for when the operation has failed
     * @param message error message to display
     * */
    data class Error(val message: String) : BookInsertApiState
}

/**
 * ViewModel class for BookDetails page
 * @property booksRepository [BookRepository] instance for handling book CRUD operations
 * @property bookListsRepository [BookListsRepository] instance for handling book list CRUD operations
 * @property key the key value to retrieve the book details from the API
 * */
class BookDetailsViewModel(private val booksRepository: BookRepository, private val bookListsRepository: BookListsRepository, private val key: String) : ViewModel() {
    /**
     * Variable for holding the current [BookGetApiState]
     * */
    var bookGetApiState: BookGetApiState by mutableStateOf(BookGetApiState.Loading)
        private set

    /**
     * Variable for holding the current [BookInsertApiState]
     * */
    var bookInsertApiState: BookInsertApiState by mutableStateOf(BookInsertApiState.Start)
        private set

    /**
     * Function to be executed on initialization, loads all the necessary data into the ViewModel
     * */
    init {
        viewModelScope.launch {
            bookGetApiState = try {
                val bookRequest = async { booksRepository.getBook(key) }
                val ratingsRequest = async { booksRepository.getRatings(key) }
                val bookListsRequest = async { bookListsRepository.getLists() }
                val book = bookRequest.await()
                val ratings = ratingsRequest.await()
                val bookLists = bookListsRequest.await()
                BookGetApiState.Success(book, ratings, bookLists)
            } catch (ex: Exception) {
                BookGetApiState.Error("An error occurred while fetching the book details")
            }
        }
    }

    /**
     * Inserts a [BookEntity] into a [BookList]
     * @param bookList list to insert book into
     * @param book [Book] to be inserted
     * @param rating rating of the to be inserted [Book]
     * */
    fun insertIntoList(bookList: BookList, book: Book, rating: Double) {
        val bookEntity: BookEntity = if (book is BookDetail) {
            BookEntity(
                key = book.key.replace("/works/", ""),
                title = book.title,
                rating = rating,
                coverId = book.covers.firstOrNull(),
            )
        } else {
            book as BookEntity
        }

        viewModelScope.launch {
            bookInsertApiState = try {
                bookListsRepository.insertIntoList(bookList, bookEntity)
                BookInsertApiState.Success("Book '${book.title}' has been successfully added to list '${bookList.listName}'")
            } catch (ex: IOException) {
                BookInsertApiState.Error("An unexpected error occurred, book '${book.title}' could not be added to list '${bookList.listName}'")
            } catch (ex: SQLiteConstraintException) {
                BookInsertApiState.Error("List '${bookList.listName}' already contains book '${book.title}'")
            }
        }
    }

    /**
     * Closes the message dialog
     * */
    fun closeAlertDialog() {
        bookInsertApiState = BookInsertApiState.Start
    }

    companion object {
        /**
         * Custom ViewModel factory to pass variables to the [BookDetailsViewModel]
         * @param key selected [Book] key
         * */
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

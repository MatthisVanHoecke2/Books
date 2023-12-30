package com.example.books.viewmodels

import com.example.books.fake.FakeBooksApiService
import com.example.books.fake.FakeDataSource
import com.example.books.fake.TestDispatcherRule
import com.example.books.fake.dao.FakeBookDao
import com.example.books.fake.dao.FakeBookListDao
import com.example.books.fake.dao.FakeBookListLineDao
import com.example.books.persistence.BooksDatabase
import com.example.books.repository.NetworkBookListRepository
import com.example.books.repository.NetworkBookRepository
import com.example.books.ui.screens.bookdetails.model.BookDetailsViewModel
import com.example.books.ui.screens.bookdetails.model.BookGetApiState
import com.example.books.ui.screens.bookdetails.model.BookInsertApiState
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class BookDetailsViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private val database = mock(BooksDatabase::class.java)

    private val booksRepository = NetworkBookRepository(
        booksApiService = FakeBooksApiService(),
        database = database,
        checkConnection = { true },
    )

    private val bookListsRepository = NetworkBookListRepository(
        database = database,
    )

    @Before
    fun setup() {
        `when`(database.bookDao()).thenReturn(FakeBookDao())
        `when`(database.bookListDao()).thenReturn(FakeBookListDao())
        `when`(database.bookListLineDao()).thenReturn(FakeBookListLineDao())
    }

    @Test
    fun bookDetailsViewModel_getBooks_verifyBookGetApiState() = runTest {
        val key = "OL1568131W"
        val viewModel = BookDetailsViewModel(booksRepository, bookListsRepository, key)
        val book = FakeDataSource.bookDetails.first { it.key == key }
        val rating = FakeDataSource.ratings.first { it.first == key }.second.summary.average!!
        val bookLists = FakeDataSource.bookLists
        assertEquals(BookGetApiState.Success(book, rating, bookLists), viewModel.bookGetApiState)
    }

    @Test
    fun bookDetailsViewModel_insertIntoList_verifyBookInsertApiState() = runTest {
        val key = "OL1568131W"
        val viewModel = BookDetailsViewModel(booksRepository, bookListsRepository, key)
        val book = FakeDataSource.bookDetails.first { it.key == key }
        val rating = FakeDataSource.ratings.first { it.first == key }.second.summary.average!!
        val bookList = FakeDataSource.bookLists.last()
        viewModel.insertIntoList(bookList, book, rating)
        assertEquals(BookInsertApiState.Success("Book '${book.title}' has been successfully added to list '${bookList.listName}'"), viewModel.bookInsertApiState)
    }
}

package com.example.books.repositories

import com.example.books.fake.FakeBooksApiService
import com.example.books.fake.FakeDataSource
import com.example.books.fake.dao.FakeBookDao
import com.example.books.fake.dao.FakeBookListDao
import com.example.books.fake.dao.FakeBookListLineDao
import com.example.books.persistence.BooksDatabase
import com.example.books.repository.NetworkBookRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class NetworkBooksRepositoryTest {
    private val database = Mockito.mock(BooksDatabase::class.java)

    private val booksRepository = NetworkBookRepository(
        booksApiService = FakeBooksApiService(),
        database = database,
        checkConnection = { true },
    )

    @Before
    fun setup() {
        Mockito.`when`(database.bookDao()).thenReturn(FakeBookDao())
        Mockito.`when`(database.bookListDao()).thenReturn(FakeBookListDao())
        Mockito.`when`(database.bookListLineDao()).thenReturn(FakeBookListLineDao())
    }

    @Test
    fun networkBooksRepository_getBooks_verifyBookIndices() = runTest {
        assertEquals(FakeDataSource.bookIndices, booksRepository.getBooks(query = "test"))
    }

    @Test
    fun networkBooksRepository_getBook_verifyBookDetails() = runTest {
        val key = "OL1568131W"
        assertEquals(FakeDataSource.bookIndices.first { it.key == key }, booksRepository.getBook(key))
    }

    @Test
    fun networkBooksRepository_getBook_verifyBookRatings() = runTest {
        val key = "OL1568131W"
        assertEquals(FakeDataSource.ratings.first { it.first == key }.second.summary.average, booksRepository.getRatings(key))
    }
}

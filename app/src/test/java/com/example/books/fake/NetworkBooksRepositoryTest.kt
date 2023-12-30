package com.example.books.fake

import com.example.books.repository.NetworkBookRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class NetworkBooksRepositoryTest {
    private val booksRepository = NetworkBookRepository(
        booksApiService = FakeBooksApiService(),
        checkConnection = { true },
    )

    @Test
    fun networkBooksRepository_getBooks_verifyBookIndices() = runTest {
        assertEquals(FakeDataSource.bookIndices, booksRepository.getBooks(query = "test"))
    }

    @ParameterizedTest
    @ValueSource(strings = ["OL1568131W", "OL1568132W"])
    fun networkBooksRepository_getBook_verifyBookDetails(key: String) = runTest {
        assertEquals(FakeDataSource.bookIndices.first { it.key == key }, booksRepository.getBook(key))
    }

    @ParameterizedTest
    @ValueSource(strings = ["OL1568131W", "OL1568132W"])
    fun networkBooksRepository_getBook_verifyBookRatings(key: String) = runTest {
        assertEquals(FakeDataSource.ratings.first { it.first == key }.second.summary.average, booksRepository.getRatings(key))
    }
}

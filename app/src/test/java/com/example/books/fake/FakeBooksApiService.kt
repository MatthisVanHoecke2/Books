package com.example.books.fake

import com.example.books.network.BooksApiService
import com.example.books.network.data.books.BookDetail
import com.example.books.network.data.books.BookResult
import com.example.books.network.data.books.Rating

class FakeBooksApiService : BooksApiService {
    override suspend fun getBooks(query: String, offset: Long, limit: Long): BookResult {
        val numFound = if (FakeDataSource.bookIndices.size.toLong() < limit) FakeDataSource.bookIndices.size.toLong() else limit
        return BookResult(numFound, FakeDataSource.bookIndices.drop(offset.toInt()).take(limit.toInt()))
    }

    override suspend fun getBook(key: String): BookDetail {
        return FakeDataSource.bookDetails.first { it.key == key }
    }

    override suspend fun getRatings(key: String): Rating {
        return FakeDataSource.ratings.first { it.first == key }.second
    }
}

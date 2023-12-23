package com.example.books.repository

import com.example.books.model.Book
import com.example.books.network.BooksApiService
import com.example.books.persistence.BooksDatabase

interface BooksRepository {
    suspend fun getBooks(query: String, offset: Long = 0, limit: Long = 25): List<Book>
    suspend fun getBook(key: String): Book
    suspend fun getRatings(key: String): Double
}

class NetworkBooksRepository(private val booksApiService: BooksApiService, private val db: BooksDatabase, private val checkRepo: () -> Boolean) : BooksRepository {

    override suspend fun getBooks(query: String, offset: Long, limit: Long): List<Book> {
        if (!checkRepo.invoke()) return db.bookDao().getAll()
        return booksApiService.getBooks(query, offset, limit).docs.map { it.copy(key = it.key.replace("/works/", "")) }
    }

    override suspend fun getBook(key: String): Book {
        if (!checkRepo.invoke()) return db.bookDao().getSingle(key)
        return booksApiService.getBook(key)
    }

    override suspend fun getRatings(key: String): Double {
        if (!checkRepo.invoke()) return db.bookDao().getRating(key)
        return booksApiService.getRatings(key).summary.average
    }
}

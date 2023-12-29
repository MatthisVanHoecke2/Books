package com.example.books.repository

import com.example.books.model.Book
import com.example.books.network.BooksApiService
import com.example.books.network.data.books.asEntityObject
import com.example.books.persistence.BooksDatabase
import com.example.books.persistence.data.books.BookEntity

/**
 * Interface for book repository
 * */
interface BookRepository {
    /**
     * Retrieves a list of books from the API
     * @param query query to search books
     * @param offset amount of items to skip
     * @param limit limit total amount of requested items
     * @return a [List] containing the queried [Book]s
     * */
    suspend fun getBooks(query: String, offset: Long = 0, limit: Long = 25): List<Book>

    /**
     * Retrieves detailed information about a book from the API
     * @param key the key value to retrieve the book details from the API
     * @return a single [Book] with the given key
     * */
    suspend fun getBook(key: String): Book

    /**
     * Retrieves detailed information about a book from the API
     * @param key the key value to retrieve the book details from the API
     * @return the selected books rating
     * */
    suspend fun getRatings(key: String): Double

    suspend fun insertBook(book: BookEntity)
}

/**
 * Real class implementation of [BookRepository]
 * @property booksApiService service for API connection
 * @property database database connection for when API is unavailable
 * @property checkConnection function callback for checking whether there is a connection with the internet
 * */
class NetworkBookRepository(private val booksApiService: BooksApiService, private val database: BooksDatabase, private val checkConnection: () -> Boolean) : BookRepository {

    override suspend fun getBooks(query: String, offset: Long, limit: Long): List<Book> {
        if (!checkConnection.invoke()) return database.bookDao().getAll(query, offset, limit)
        return booksApiService.getBooks(query, offset, limit).docs.map { it.copy(key = it.key.replace("/works/", "")) }
    }

    override suspend fun getBook(key: String): Book {
        if (!checkConnection.invoke()) return database.bookDao().getSingle(key)
        val ratings = getRatings(key)
        val book = booksApiService.getBook(key)
        database.bookDao().update(book.asEntityObject(ratings))
        return book
    }

    override suspend fun getRatings(key: String): Double {
        if (!checkConnection.invoke()) return database.bookDao().getRating(key)
        return booksApiService.getRatings(key).summary.average ?: 0.0
    }

    override suspend fun insertBook(book: BookEntity) {
        database.bookDao().insert(book)
    }
}

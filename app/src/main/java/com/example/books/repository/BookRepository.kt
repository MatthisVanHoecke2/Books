package com.example.books.repository

import com.example.books.model.Book
import com.example.books.network.BooksApiService
import com.example.books.persistence.BooksDatabase
import java.util.concurrent.TimeoutException

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
}

/**
 * Real class implementation of [BookRepository]
 * @property booksApiService service for API connection
 * @property database database connection for when API is unavailable
 * @property checkConnection function callback for checking whether there is a connection with the internet
 * */
class NetworkBookRepository(private val booksApiService: BooksApiService, private val database: BooksDatabase, private val checkConnection: () -> Boolean) : BookRepository {

    override suspend fun getBooks(query: String, offset: Long, limit: Long): List<Book> {
        if (!checkConnection.invoke()) return database.bookDao().getAll()
        return try {
            booksApiService.getBooks(query, offset, limit).docs.map { it.copy(key = it.key.replace("/works/", "")) }
        } catch (exception: TimeoutException) {
            database.bookDao().getAll()
        }
    }

    override suspend fun getBook(key: String): Book {
        if (!checkConnection.invoke()) return database.bookDao().getSingle(key)
        return try {
            booksApiService.getBook(key)
        } catch (exception: TimeoutException) {
            database.bookDao().getSingle(key)
        }
    }

    override suspend fun getRatings(key: String): Double {
        if (!checkConnection.invoke()) return database.bookDao().getRating(key)
        return try {
            booksApiService.getRatings(key).summary.average ?: 0.0
        } catch (exception: TimeoutException) {
            database.bookDao().getRating(key)
        }
    }
}

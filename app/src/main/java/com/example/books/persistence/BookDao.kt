package com.example.books.persistence

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.books.persistence.data.books.BookEntity

/**
 * DAO for handling book CRUD operations to the Room database
 * */
@Dao
interface BookDao {
    /**
     * Updates a book
     * @param book entity instance of book
     * */
    @Update
    suspend fun update(book: BookEntity)

    /**
     * Get a list of books, filtered by title
     * @param query text to filter list by
     * @param offset amount of items to skip
     * @param limit restrict the amount of items to be returned
     * @return a list of books
     * */
    @Query("SELECT * FROM Book WHERE title LIKE '%' || :query || '%' LIMIT :limit OFFSET :offset")
    suspend fun getAll(query: String = "", offset: Long = 0, limit: Long = 25): List<BookEntity>

    /**
     * Get a single book entity by its key
     * @param key primary key to retrieve book
     * @return a book entity
     * */
    @Query("SELECT * FROM Book WHERE book_key = :key")
    suspend fun getSingle(key: String): BookEntity

    /**
     * Get a books rating by its key
     * @param key primary key to retrieve book
     * @return a books rating
     * */
    @Query("SELECT rating FROM Book WHERE book_key = :key")
    suspend fun getRating(key: String): Double
}

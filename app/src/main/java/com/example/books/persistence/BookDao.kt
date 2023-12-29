package com.example.books.persistence

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.books.persistence.data.books.BookEntity

@Dao
interface BookDao {
    suspend fun insert(book: BookEntity)

    @Update
    suspend fun update(book: BookEntity)

    @Query("SELECT * FROM Book WHERE title LIKE '%' || :query || '%' LIMIT :limit OFFSET :offset")
    suspend fun getAll(query: String = "", offset: Long = 0, limit: Long = 25): List<BookEntity>

    @Query("SELECT * FROM Book WHERE book_key = :key")
    suspend fun getSingle(key: String): BookEntity

    @Query("SELECT rating FROM Book WHERE book_key = :key")
    suspend fun getRating(key: String): Double
}

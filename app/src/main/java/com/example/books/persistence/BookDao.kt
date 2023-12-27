package com.example.books.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.books.persistence.data.books.BookEntity

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: BookEntity)

    @Query("SELECT * FROM Book WHERE title LIKE '%' || :query || '%'")
    suspend fun getAll(query: String = ""): List<BookEntity>

    @Query("SELECT * FROM Book WHERE book_key = :key")
    suspend fun getSingle(key: String): BookEntity

    @Query("SELECT rating FROM Book WHERE book_key = :key")
    suspend fun getRating(key: String): Double
}

package com.example.books.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.books.data.books.BookDetail

@Dao
interface BookDao {
    @Insert
    suspend fun insert(book: BookDetail)

    @Query("SELECT * FROM Book")
    suspend fun getAll(): List<BookDetail>
}

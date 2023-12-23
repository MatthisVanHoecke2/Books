package com.example.books.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.books.persistence.data.booklists.BookList

@Dao
interface BookListDao {
    @Insert
    suspend fun insert(bookList: BookList)

    @Delete
    suspend fun delete(bookList: BookList)

    @Update
    suspend fun update(bookList: BookList)

    @Query("SELECT * FROM BookList")
    suspend fun getAll(): List<BookList>
}

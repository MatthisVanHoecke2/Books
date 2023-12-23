package com.example.books.persistence

import androidx.room.Dao
import androidx.room.Insert
import com.example.books.persistence.data.booklists.BookListLine

@Dao
interface BookListLineDao {
    @Insert
    fun insert(bookListLine: BookListLine)
}

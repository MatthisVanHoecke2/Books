package com.example.books.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.books.persistence.data.booklists.BookList
import com.example.books.persistence.data.books.BookEntity

@Dao
interface BookListDao {
    @Insert
    suspend fun insert(bookList: BookList): Long

    @Delete
    suspend fun delete(bookList: BookList)

    @Update
    suspend fun update(bookList: BookList)

    @Query("SELECT * FROM BookList")
    suspend fun getAll(): List<BookList>

    @Query("SELECT b.* FROM book b JOIN booklistline bll ON b.book_key = bll.book_key JOIN booklist bl ON bll.booklist_id = bl.booklist_id WHERE bl.booklist_id = :id")
    suspend fun getAllBooksFromList(id: Long): List<BookEntity>
}

package com.example.books.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.books.data.authors.Author

@Dao
interface AuthorDao {
    @Insert
    suspend fun insert(author: Author)

    @Query("SELECT * FROM Author")
    suspend fun getAll(): List<Author>
}

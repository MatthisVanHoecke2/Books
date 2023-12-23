package com.example.books.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.books.persistence.data.authors.AuthorEntity

@Dao
interface AuthorDao {
    @Insert
    suspend fun insert(author: AuthorEntity)

    @Query("SELECT * FROM Author")
    suspend fun getAll(): List<AuthorEntity>
}

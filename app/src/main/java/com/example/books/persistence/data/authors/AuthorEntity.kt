package com.example.books.persistence.data.authors

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "author")
data class AuthorEntity(
    val name: String,
    @ColumnInfo(name = "author_key")
    @PrimaryKey
    val authorKey: String,
)

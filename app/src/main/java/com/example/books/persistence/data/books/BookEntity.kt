package com.example.books.persistence.data.books

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.books.model.Book

@Entity(tableName = "book")
data class BookEntity(
    @ColumnInfo(name = "book_key")
    @PrimaryKey
    override val key: String,
    override val title: String,
    @ColumnInfo(name = "first_publish_year")
    val publishYear: Int? = null,
    val rating: Double,
    @ColumnInfo(name = "cover_id")
    override val coverId: Long? = null,
) : Book

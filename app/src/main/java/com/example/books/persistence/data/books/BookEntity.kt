package com.example.books.persistence.data.books

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.books.model.Book

/**
 * Entity class for a book
 * @property key primary key for retrieving a book
 * @property title book title
 * @property rating average book rating
 * @property coverId book cover id linked to a jpeg image
 * */
@Entity(tableName = "book")
data class BookEntity(
    @ColumnInfo(name = "book_key")
    @PrimaryKey
    override val key: String,
    override val title: String,
    val rating: Double,
    @ColumnInfo(name = "cover_id")
    override val coverId: Long? = null,
) : Book

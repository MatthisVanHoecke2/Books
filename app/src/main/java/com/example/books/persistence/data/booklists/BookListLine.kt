package com.example.books.persistence.data.booklists

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.books.persistence.data.books.BookEntity

@Entity(
    primaryKeys = ["booklist_id", "book_key"],
    foreignKeys = [
        ForeignKey(
            entity = BookList::class,
            parentColumns = ["booklist_id"],
            childColumns = ["booklist_id"],
            onDelete = ForeignKey.CASCADE,
        ), ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["book_key"],
            childColumns = ["book_key"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class BookListLine(
    @ColumnInfo(name = "booklist_id")
    val bookListId: Long,
    @ColumnInfo(name = "book_key")
    val bookKey: String,
)

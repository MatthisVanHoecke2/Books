package com.example.books.model

/**
 * Book interface for persistence
 * @property key primary key for retrieving book
 * @property title book title
 * @property coverId book cover id linked to a jpeg image
 * */
interface Book {
    val key: String
    val title: String
    val coverId: Long?
}

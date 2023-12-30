package com.example.books.network.data.books

import androidx.room.Ignore
import com.example.books.model.Book
import com.example.books.persistence.data.books.BookEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Index object class for retrieving general book data from the API
 * @property key primary key for retrieving book
 * @property title book title
 * @property coverId book cover id, linked to a jpeg image
 * */
@Serializable
data class BookIndex(
    override val key: String,
    override val title: String,
    @SerialName(value = "cover_i")
    override val coverId: Long? = null,
) : Book

/**
 * Index object class for retrieving detailed book data from the API
 * @property key primary key for retrieving book
 * @property title book title
 * @property coverId empty cover id for interface
 * @property covers multiple different book cover id's linked to a jpeg image
 * */
@Serializable
data class BookDetail(
    @SerialName(value = "key")
    override val key: String,
    val covers: List<Long> = emptyList(),
    override val title: String,
    @Ignore override val coverId: Long? = null,
) : Book

/**
 * Extension function for converting [BookDetail] to [BookEntity]
 * @param rating average book rating
 * @return a new BookEntity instance
 * */
fun BookDetail.asEntityObject(rating: Double): BookEntity {
    return BookEntity(
        key = this.key.replace("/works/", ""),
        title = this.title,
        rating = rating,
        coverId = this.covers.firstOrNull(),
    )
}

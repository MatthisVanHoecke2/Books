package com.example.books.network.data.books

import com.example.books.model.Book
import com.example.books.persistence.data.books.BookEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookIndex(
    override val key: String,
    override val title: String,
    @SerialName(value = "cover_i")
    override val coverId: Long? = null,
) : Book

@Serializable
data class BookDetail(
    @SerialName(value = "key")
    override val key: String,
    val covers: List<Long> = emptyList(),
    override val title: String,
    @SerialName(value = "first_publish_year")
    val publishYear: Int? = null,
    override val coverId: Long? = null,
) : Book

fun BookDetail.asEntityObject(rating: Double): BookEntity {
    return BookEntity(
        key = this.key.replace("/works/", ""),
        title = this.title,
        publishYear = this.publishYear,
        rating = rating,
        coverId = this.covers.first(),
    )
}
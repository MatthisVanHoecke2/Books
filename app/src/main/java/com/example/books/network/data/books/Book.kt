package com.example.books.network.data.books

import com.example.books.model.Book
import com.example.books.network.data.authors.AuthorLine
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookIndex(
    override val key: String,
    override val title: String,
    @SerialName(value = "cover_i")
    val coverId: Long? = null,
) : Book

@Serializable
data class BookDetail(
    @SerialName(value = "key")
    override val key: String,
    val covers: List<Long> = emptyList(),
    override val title: String,
    @SerialName(value = "authors")
    val authors: List<AuthorLine> = emptyList(),
    @SerialName(value = "first_publish_year")
    val publishYear: Int? = null,
) : Book

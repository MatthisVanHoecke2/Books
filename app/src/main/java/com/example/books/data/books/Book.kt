package com.example.books.data.books

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    @SerialName(value = "cover_i")
    val coverId: Long,
    @SerialName(value = "edition_count")
    val edition: Long,
    val title: String,
    @SerialName(value = "author_name")
    val author: String,
    @SerialName(value = "first_publish_year")
    val publishYear: Int,
)

package com.example.books.data.books

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    @SerialName(value = "cover_i")
    val coverId: Long? = null,
    @SerialName(value = "edition_count")
    val edition: Long? = null,
    val title: String,
    @SerialName(value = "author_name")
    val author: List<String?> = emptyList(),
    val contributor: List<String?> = emptyList(),
    @SerialName(value = "first_publish_year")
    val publishYear: Int? = null,
)

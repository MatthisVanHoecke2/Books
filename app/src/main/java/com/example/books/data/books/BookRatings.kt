package com.example.books.data.books

import kotlinx.serialization.Serializable

@Serializable
data class BookRatings(
    val summary: Summary = Summary()
)

@Serializable
data class Summary(
    val average: Double = 0.0,
    val count: Int = 0,
)

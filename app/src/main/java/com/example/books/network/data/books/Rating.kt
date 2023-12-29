package com.example.books.network.data.books

import kotlinx.serialization.Serializable

/**
 * Average rating of a specific book with a minimum value of 0 and maximum value of 5
 * @property summary object containing average rating and amount of users who rated the book
 * */
@Serializable
data class Rating(
    val summary: Summary = Summary(),
)

/**
 * Summary class containing the average rating and amount of users who rated the book
 * @property average average rating of a book
 * @property count amount of users who rated the book
 * */
@Serializable
data class Summary(
    val average: Double? = 0.0,
    val count: Int = 0,
)

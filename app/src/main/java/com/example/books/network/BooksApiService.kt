package com.example.books.network

import com.example.books.network.data.books.BookDetail
import com.example.books.network.data.books.BookResult
import com.example.books.network.data.books.Rating
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Service for retrieving data from the API
 * */
interface BooksApiService {
    /**
     * Retrieves a list of books from the API
     * @param query query to search books
     * @param offset amount of items to skip
     * @param limit limit total amount of requested items
     * @return a [BookResult] containing the queried books
     * */
    @GET("/search.json")
    suspend fun getBooks(@Query("q") query: String, @Query("offset") offset: Long = 0, @Query("limit") limit: Long = 25): BookResult

    /**
     * Retrieves detailed information about a book from the API
     * @param key the key value to retrieve the book details from the API
     * @return [BookDetail] containing all the book details
     * */
    @GET("/works/{key}.json")
    suspend fun getBook(@Path("key") key: String): BookDetail

    /**
     * Retrieves a books ratings from the API
     * @param key the key value to retrieve the book details from the API
     * @return [Rating] containing the ratings of a specific book
     * */
    @GET("/works/{key}/ratings.json")
    suspend fun getRatings(@Path("key") key: String): Rating
}

package com.example.books.network

import com.example.books.data.books.BookDetail
import com.example.books.data.books.BookRatings
import com.example.books.data.books.BookResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private val retrofit = createRetrofit("https://openlibrary.org")

interface BooksApiService {
    @GET("/search.json")
    suspend fun getBooks(@Query("q") query: String, @Query("offset") offset: Long = 0, @Query("limit") limit: Long = 25): BookResult

    @GET("/works/{key}.json")
    suspend fun getBook(@Path("key") key: String): BookDetail

    @GET("/works/{key}/ratings.json")
    suspend fun getRatings(@Path("key") key: String): BookRatings
}

object BooksApi {
    val retrofitService: BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)
    }
}

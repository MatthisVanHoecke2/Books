package com.example.books.fake.dao

import com.example.books.fake.FakeDataSource
import com.example.books.persistence.BookDao
import com.example.books.persistence.data.books.BookEntity

class FakeBookDao : BookDao {
    override suspend fun update(book: BookEntity) {
        val list = FakeDataSource.bookEntities.toMutableList()
        list.removeIf { it.key == book.key }
        list.add(book)
        FakeDataSource.bookEntities = list
    }

    override suspend fun getAll(query: String, offset: Long, limit: Long): List<BookEntity> {
        return FakeDataSource.bookEntities.drop(offset.toInt()).take(limit.toInt())
    }

    override suspend fun getSingle(key: String): BookEntity {
        return FakeDataSource.bookEntities.first { it.key == key }
    }

    override suspend fun getRating(key: String): Double {
        return FakeDataSource.bookEntities.first { it.key == key }.rating
    }
}

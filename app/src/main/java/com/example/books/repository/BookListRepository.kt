package com.example.books.repository

import com.example.books.model.Book
import com.example.books.persistence.BooksDatabase
import com.example.books.persistence.data.booklists.BookList
import com.example.books.persistence.data.booklists.BookListLine
import com.example.books.persistence.data.books.BookEntity

/**
 * Interface for book list repository
 * */
interface BookListsRepository {
    /**
     * Retrieves a list of book lists from the Room database
     * @return a [List] containing the queried [BookList]s
     * */
    suspend fun getLists(): List<BookList>

    /**
     * Creates a book list with a given name
     * @param name book list name
     * @return the book list id
     * */
    suspend fun createList(name: String): Long

    /**
     * Deletes a book list
     * @param bookList book list entity to delete
     * */
    suspend fun deleteList(bookList: BookList)

    /**
     * Deletes a book list line
     * @param bookListLine book list line entity to delete
     * */
    suspend fun deleteBookFromList(bookListLine: BookListLine)

    /**
     * Updates a book list
     * @param bookList book list entity to delete
     * */
    suspend fun updateList(bookList: BookList)

    /**
     * Retrieves a list of books by book list id
     * @param id primary key of the book list to retrieve
     * @return the list of books inside a book list
     * */
    suspend fun getListOfBooksById(id: Long): List<Book>

    /**
     * Retrieves a single [BookList]
     * @param id primary key of the book list to retrieve
     * @return an instance of [BookList]
     * */
    suspend fun getBookListById(id: Long): BookList

    /**
     * Inserts a specific book into a book list
     * @param bookList book list to insert book into
     * @param book book to insert into book list
     * */
    suspend fun insertIntoList(bookList: BookList, book: BookEntity)
}

class NetworkBookListRepository(private val database: BooksDatabase) : BookListsRepository {
    override suspend fun getLists(): List<BookList> {
        return database.bookListDao().getAll()
    }

    override suspend fun createList(name: String): Long {
        return database.bookListDao().insert(BookList(listName = name))
    }

    override suspend fun deleteList(bookList: BookList) {
        database.bookListDao().delete(bookList)
    }

    override suspend fun deleteBookFromList(bookListLine: BookListLine) {
        database.bookListLineDao().deleteBookList(bookListLine)
    }

    override suspend fun updateList(bookList: BookList) {
        database.bookListDao().update(bookList)
    }

    override suspend fun getListOfBooksById(id: Long): List<Book> {
        return database.bookListDao().getAllBooksFromList(id)
    }

    override suspend fun getBookListById(id: Long): BookList {
        return database.bookListDao().getBookListById(id)
    }

    override suspend fun insertIntoList(bookList: BookList, book: BookEntity) {
        database.bookListLineDao().insertWithListLine(bookList, book)
    }
}

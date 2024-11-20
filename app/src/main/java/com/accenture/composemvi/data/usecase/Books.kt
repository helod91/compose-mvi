package com.accenture.composemvi.data.usecase

import com.accenture.composemvi.data.model.Book
import kotlin.random.Random

object Books {

    private val books = mutableListOf<Book>()

    fun getBooks(): List<Book> {
        if (books.isEmpty()) {
            for (i in 0..10) {
                books.add(
                    Book(
                        id = i.toString(),
                        coverImageUrl = "https://picsum.photos/600?random=$i",
                        title = "Book $i",
                        author = "John Doe the $i.",
                        genre = "Fiction $i",
                        price = (1..25).random(),
                        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                    )
                )
            }
        }
        return books
    }

    fun getBookById(id: String): Book {
        return books.first { it.id == id }
    }
}
package com.accenture.composemvi.data.usecase

import com.accenture.composemvi.data.model.Book
import kotlinx.coroutines.delay

class GetBooksUseCase {

    suspend operator fun invoke() : List<Book> {
        delay(2000)
        return Books.getBooks()
    }
}
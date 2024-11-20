package com.accenture.composemvi.data.usecase

import com.accenture.composemvi.data.model.Book
import kotlinx.coroutines.delay

class GetBookUseCase {

    suspend operator fun invoke(id: String) : Book {
        delay(2000)
       return Books.getBookById(id)
    }
}
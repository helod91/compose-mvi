package com.accenture.composemvi.ui.features.booklist

import com.accenture.composemvi.data.model.Book

interface BookListContract {

    data class BookListViewState(
        val books: List<Book> = emptyList(),
        val isLoading: Boolean = false
    )

    sealed interface BookListViewEvent {
        data object LoadBooks : BookListViewEvent
        data class BookSelected(val bookId: String) : BookListViewEvent
    }

    sealed interface BookListSideEffect {
        data object BooksLoaded : BookListSideEffect
        sealed interface Navigation : BookListSideEffect {
            data class GoToBookDetails(val bookId: String) : Navigation
        }
    }
}
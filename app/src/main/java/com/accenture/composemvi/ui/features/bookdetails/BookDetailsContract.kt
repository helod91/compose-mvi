package com.accenture.composemvi.ui.features.bookdetails

import com.accenture.composemvi.data.model.Book

interface BookDetailsContract {
    data class BookDetailsViewState(
        val book: Book? = null,
        val isLoading: Boolean = false
    )

    sealed interface BookDetailsViewEvent {
        data class LoadBook(val bookId: String) : BookDetailsViewEvent
        data object OnBackClicked : BookDetailsViewEvent
    }

    sealed interface BookDetailsSideEffect {
        data object BookLoaded : BookDetailsSideEffect
        sealed interface Navigation : BookDetailsSideEffect {
            data object GoBack : Navigation
        }
    }
}
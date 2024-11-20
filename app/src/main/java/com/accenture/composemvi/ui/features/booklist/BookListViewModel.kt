package com.accenture.composemvi.ui.features.booklist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accenture.composemvi.data.usecase.GetBooksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BookListViewModel(
    private val getBooksUseCase: GetBooksUseCase
) : ViewModel() {

    private val _effect: Channel<BookListContract.BookListSideEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val _state = mutableStateOf(BookListContract.BookListViewState())
    val state: State<BookListContract.BookListViewState> = _state

    fun handleEvent(event: BookListContract.BookListViewEvent) {
        when (event) {
            is BookListContract.BookListViewEvent.LoadBooks -> loadBooks()
            is BookListContract.BookListViewEvent.BookSelected ->
                setEffect(BookListContract.BookListSideEffect.Navigation.GoToBookDetails(event.bookId))
        }
    }

    private fun setEffect(effect: BookListContract.BookListSideEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    private fun loadBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = true)
            val books = getBooksUseCase()
            _state.value = _state.value.copy(books = books, isLoading = false)
            setEffect(BookListContract.BookListSideEffect.BooksLoaded)
        }
    }
}
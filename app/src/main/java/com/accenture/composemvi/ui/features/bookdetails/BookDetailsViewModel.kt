package com.accenture.composemvi.ui.features.bookdetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accenture.composemvi.data.usecase.GetBookUseCase
import com.accenture.composemvi.data.usecase.GetBooksUseCase
import com.accenture.composemvi.ui.features.booklist.BookListContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val getBookUseCase: GetBookUseCase
) : ViewModel() {

    private val _effect: Channel<BookDetailsContract.BookDetailsSideEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val _state = mutableStateOf(BookDetailsContract.BookDetailsViewState())
    val state: State<BookDetailsContract.BookDetailsViewState> = _state

    fun handleEvent(event:BookDetailsContract.BookDetailsViewEvent) {
        when (event) {
            is BookDetailsContract.BookDetailsViewEvent.LoadBook -> loadBook(event.bookId)
            is BookDetailsContract.BookDetailsViewEvent.OnBackClicked ->
                setEffect(BookDetailsContract.BookDetailsSideEffect.Navigation.GoBack)
        }
    }

    private fun setEffect(effect: BookDetailsContract.BookDetailsSideEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    private fun loadBook(bookId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = true)
            val book = getBookUseCase(bookId)
            _state.value = _state.value.copy(book = book, isLoading = false)
            setEffect(BookDetailsContract.BookDetailsSideEffect.BookLoaded)
        }
    }
}
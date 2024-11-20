package com.accenture.composemvi.ui.features.booklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import coil3.compose.AsyncImage
import com.accenture.composemvi.data.model.Book
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    state: BookListContract.BookListViewState,
    onEvent: (BookListContract.BookListViewEvent) -> Unit,
    onNavigation: (BookListContract.BookListSideEffect.Navigation) -> Unit,
    sideEffects: Flow<BookListContract.BookListSideEffect>,
    modifier: Modifier = Modifier
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(sideEffects, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
            sideEffects.collect { sideEffect ->
                when (sideEffect) {
                    is BookListContract.BookListSideEffect.Navigation -> onNavigation(sideEffect)
                    is BookListContract.BookListSideEffect.BooksLoaded ->
                        snackbarHostState.showSnackbar("Books loaded")
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        onEvent(BookListContract.BookListViewEvent.LoadBooks)
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Book List")
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(state.books) { book ->
                    BookItem(book, onEvent)
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun BookItem(
    book: Book,
    onEvent: (BookListContract.BookListViewEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onEvent(BookListContract.BookListViewEvent.BookSelected(book.id))
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(80.dp),
                model = book.coverImageUrl,
                contentDescription = book.title
            )

            Spacer(Modifier.width(16.dp))

            Column {
                Text(
                    text = book.title,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = book.author
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = book.genre
                )
            }

            Spacer(Modifier.width(64.dp))

            Text(
                text = "$${book.price}"
            )
        }

        HorizontalDivider()
    }
}
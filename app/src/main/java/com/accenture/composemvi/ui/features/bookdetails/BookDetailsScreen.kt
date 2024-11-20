package com.accenture.composemvi.ui.features.bookdetails

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(
    state: BookDetailsContract.BookDetailsViewState,
    onEvent: (BookDetailsContract.BookDetailsViewEvent) -> Unit,
    onNavigation: (BookDetailsContract.BookDetailsSideEffect.Navigation) -> Unit,
    sideEffects: Flow<BookDetailsContract.BookDetailsSideEffect>,
    bookId: String?,
    modifier: Modifier = Modifier
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(sideEffects, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
            sideEffects.collect { sideEffect ->
                when (sideEffect) {
                    is BookDetailsContract.BookDetailsSideEffect.Navigation -> onNavigation(
                        sideEffect
                    )

                    is BookDetailsContract.BookDetailsSideEffect.BookLoaded ->
                        snackbarHostState.showSnackbar("Book loaded")
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        bookId?.let {
            onEvent(BookDetailsContract.BookDetailsViewEvent.LoadBook(it))
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Book details")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onEvent(BookDetailsContract.BookDetailsViewEvent.OnBackClicked)
                        },
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    )
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
            state.book?.let { book ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(128.dp),
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
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Price: $${book.price}"
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = book.description,
                        modifier = Modifier.padding(16.dp)
                    )
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
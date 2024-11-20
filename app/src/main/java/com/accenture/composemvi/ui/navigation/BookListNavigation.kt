package com.accenture.composemvi.ui.navigation

import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.accenture.composemvi.ui.features.booklist.BookListContract
import com.accenture.composemvi.ui.features.booklist.BookListScreen
import com.accenture.composemvi.ui.features.booklist.BookListViewModel
import org.koin.androidx.compose.koinViewModel

const val BOOK_LIST_ROUTE = "book_list"

fun NavGraphBuilder.bookListScreenGraph(
    navController: NavController
) {
    composable(BOOK_LIST_ROUTE) {
        val viewModel: BookListViewModel = koinViewModel()
        val state by viewModel.state

        BookListScreen(
            state = state,
            sideEffects = viewModel.effect,
            onEvent = viewModel::handleEvent,
            onNavigation = { navigationEffect ->
                when (navigationEffect) {
                    is BookListContract.BookListSideEffect.Navigation.GoToBookDetails ->
                        navController.navigateToBookDetails(navigationEffect.bookId)
                }
            }
        )
    }
}
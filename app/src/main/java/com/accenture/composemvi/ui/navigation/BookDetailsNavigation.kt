package com.accenture.composemvi.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.accenture.composemvi.ui.features.bookdetails.BookDetailsContract
import com.accenture.composemvi.ui.features.bookdetails.BookDetailsScreen
import com.accenture.composemvi.ui.features.bookdetails.BookDetailsViewModel
import org.koin.androidx.compose.koinViewModel

const val BOOK_DETAILS_PATH = "book_details/"
const val BOOK_ID_KEY = "bookId"
const val BOOK_DETAILS_ROUTE = "$BOOK_DETAILS_PATH{$BOOK_ID_KEY}"

fun NavController.navigateToBookDetails(bookId: String) {
    navigate(BOOK_DETAILS_ROUTE.replace("{$BOOK_ID_KEY}", bookId))
}

fun NavGraphBuilder.bookDetailsScreenNavGraph(navController: NavController) {
    composable(BOOK_DETAILS_ROUTE) {
        val bookId = it.arguments?.getString(BOOK_ID_KEY)
        val viewModel: BookDetailsViewModel = koinViewModel()
        val state by  viewModel.state

        BookDetailsScreen(
            state = state,
            sideEffects = viewModel.effect,
            onEvent = viewModel::handleEvent,
            onNavigation = { navigationEffect ->
                when (navigationEffect) {
                    BookDetailsContract.BookDetailsSideEffect.Navigation.GoBack ->
                        navController.popBackStack()
                }
            },
            bookId = bookId
        )
    }
}
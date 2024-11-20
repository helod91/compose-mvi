package com.accenture.composemvi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = BOOK_LIST_ROUTE
    ) {
        bookListScreenGraph(navController)
        bookDetailsScreenNavGraph(navController)
    }
}
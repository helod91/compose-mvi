package com.accenture.composemvi.di

import com.accenture.composemvi.ui.features.bookdetails.BookDetailsViewModel
import com.accenture.composemvi.ui.features.booklist.BookListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { BookListViewModel(get()) }
    viewModel { BookDetailsViewModel(get()) }
}
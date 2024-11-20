package com.accenture.composemvi.di

import com.accenture.composemvi.data.usecase.GetBookUseCase
import com.accenture.composemvi.data.usecase.GetBooksUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetBooksUseCase() }
    factory { GetBookUseCase() }
}
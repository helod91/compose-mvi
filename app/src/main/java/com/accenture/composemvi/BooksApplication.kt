package com.accenture.composemvi

import android.app.Application
import com.accenture.composemvi.di.useCaseModule
import com.accenture.composemvi.di.viewModelModule
import org.koin.core.context.startKoin

class BooksApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                useCaseModule,
                viewModelModule
            )
        }
    }
}
package features.booklist.di

import features.booklist.data.remote.GoogleBooksHttpClient
import features.booklist.data.remote.GoogleBooksRemoteDataSource
import features.booklist.data.repository.BooksRepositoryImpl
import features.booklist.domain.repository.BooksRepository
import features.booklist.domain.usecase.*
import features.booklist.presentation.BookListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val bookModule = module {
    // Data Layer
    single { GoogleBooksHttpClient() }
    single { GoogleBooksRemoteDataSource(get()) }
    single { BooksRepositoryImpl(get()) } bind BooksRepository::class

    // Use Cases
    factoryOf(::GetTrendingBooksUseCase)
    factoryOf(::SearchBooksUseCase)

    // View Model
    factoryOf(::BookListViewModel)
} 
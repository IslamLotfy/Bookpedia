package features.booklist.di

import di.getBookpediaDatabase
import features.booklist.data.local.BookpediaDatabase
import features.booklist.data.remote.GoogleBooksHttpClient
import features.booklist.data.remote.GoogleBooksRemoteDataSource
import features.booklist.data.repository.BooksRepositoryImpl
import features.booklist.data.repository.FavoriteBookRepositoryImpl
import features.booklist.domain.repository.BooksRepository
import features.booklist.domain.repository.FavoriteBookRepository
import features.booklist.domain.usecase.*
import features.booklist.presentation.BookListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val bookModule = module {
    // Database - using the context provided by platform-specific modules
    single { getBookpediaDatabase(get()) }
    single { get<BookpediaDatabase>().favoriteBookDao() }

    // Data Layer
    single { GoogleBooksHttpClient() }
    single { GoogleBooksRemoteDataSource(get()) }
    single { BooksRepositoryImpl(get()) } bind BooksRepository::class
    single { FavoriteBookRepositoryImpl(get()) } bind FavoriteBookRepository::class

    // Use Cases
    factoryOf(::GetTrendingBooksUseCase)
    factoryOf(::SearchBooksUseCase)
    factoryOf(::GetFavoriteBooksUseCase)
    factoryOf(::IsFavoriteBookUseCase)
    factoryOf(::ToggleFavoriteBookUseCase)
    factoryOf(::GetBookByIdUseCase)

    // View Model
    factoryOf(::BookListViewModel)
}
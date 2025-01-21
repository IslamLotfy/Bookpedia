package di

import booklist.data.remoteDS.BooksRemoteDataSource
import booklist.data.repository.BooksRepositoryImpl
import booklist.domain.repository.BooksRepository
import booklist.domain.usecases.GetBooksTrendingThisMonthUseCase
import booklist.domain.usecases.GetBooksTrendingThisWeekUseCase
import booklist.domain.usecases.GetBooksTrendingThisYearUseCase
import booklist.domain.usecases.GetBooksTrendingTodayUseCase
import booklist.presentation.TrendingBooksViewModel
import core.network.BookpediaHttpClient
import io.ktor.client.*
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val dataModule = module {
    single<BookpediaHttpClient> {
        BookpediaHttpClient(get())
    }

    single<HttpClient> {
        val bookpediaHttpClient: BookpediaHttpClient = get()
        bookpediaHttpClient.create()
    }

    single<BooksRepository> {
        BooksRepositoryImpl(get())
    }

    single<BooksRemoteDataSource> {
        BooksRemoteDataSource(get())
    }

}

val domainModule = module {
    single<GetBooksTrendingTodayUseCase> {
        GetBooksTrendingTodayUseCase(get())
    }

    single<GetBooksTrendingThisWeekUseCase> {
        GetBooksTrendingThisWeekUseCase(get())
    }

    single<GetBooksTrendingThisMonthUseCase> {
        GetBooksTrendingThisMonthUseCase(get())
    }

    single<GetBooksTrendingThisYearUseCase> {
        GetBooksTrendingThisYearUseCase(get())
    }
}

val presentationModule = module {
    viewModel {
        TrendingBooksViewModel(get(),get(),get(),get())
    }
}
expect val platformModule: Module


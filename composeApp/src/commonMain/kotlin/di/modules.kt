package di

import core.network.BookpediaHttpClient
import features.booklist.di.bookModule
import io.ktor.client.*
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule = module {
    single<BookpediaHttpClient> {
        BookpediaHttpClient(get())
    }

    single<HttpClient> {
        val bookpediaHttpClient: BookpediaHttpClient = get()
        bookpediaHttpClient.create()
    }
}

expect fun platformModule(context: Any = Any()): Module

fun createAppModule(context: Any = Any()) = module {
    includes(platformModule(context))
    includes(dataModule)
    includes(bookModule)
}

val appModule = createAppModule()

expect fun getBookpediaDatabase(context: Any): features.booklist.data.local.BookpediaDatabase



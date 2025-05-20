package di


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

}

expect val platformModule: Module


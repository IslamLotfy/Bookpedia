package di

import core.network.BookpediaHttpClient
import io.ktor.client.*
import io.ktor.client.engine.*
import org.koin.core.module.Module
import org.koin.dsl.module
import io.ktor.client.engine.darwin.*


actual val platformModule = module {
    single<HttpClientEngine> {
        Darwin.create()
    }
}
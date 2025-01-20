package di

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import org.koin.dsl.module

actual val platformModule = module {
    single<HttpClientEngine> {
        OkHttp.create()
    }
}
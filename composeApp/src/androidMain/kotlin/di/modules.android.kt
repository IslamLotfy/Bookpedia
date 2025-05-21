package di

import android.content.Context
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import org.koin.dsl.module
import org.koin.core.module.Module

actual fun platformModule(context: Any): Module = module {
    single<HttpClientEngine> {
        OkHttp.create()
    }
}
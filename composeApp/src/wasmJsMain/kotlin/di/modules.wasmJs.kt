package di
import io.ktor.client.engine.*
import io.ktor.client.engine.js.*
import org.koin.dsl.module

actual val platformModule =  module {
    single<HttpClientEngine> {
        Js.create()
    }
}
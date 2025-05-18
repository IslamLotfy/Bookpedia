package core.network

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.resources.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class BookpediaHttpClient(private val clientEngine: HttpClientEngine){
    fun create(): HttpClient {
        return HttpClient(clientEngine){
            expectSuccess = true
            install(ContentNegotiation){
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    isLenient = true
                    explicitNulls = false
                    prettyPrint = true
                })
            }

            install(HttpTimeout){
                requestTimeoutMillis = 660_000
            }

            install(Logging){
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

            defaultRequest {
                header("Content-Type","application/json")
                url(urlString = "https://openlibrary.org/")
            }
        }
    }
}
package features.booklist.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class GoogleBooksHttpClient {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 15000
            socketTimeoutMillis = 15000
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "www.googleapis.com"
                path("books/v1/")
            }
        }
    }

    companion object {
        // This is a simple obfuscation approach for demonstration purposes
        // In a production app, consider:
        // 1. Using a secure storage mechanism
        // 2. Implementing proper key management
        // 3. Using environment variables or build config
        private val keyParts = listOf(
            "AIza",
            "SyBmUE4_6q",
            "FfOEn7tb8jBq",
            "Ighm4OCx9gbac"
        )
        
        // Assemble the key only when needed
        val API_KEY: String
            get() = keyParts.joinToString("")
    }
}
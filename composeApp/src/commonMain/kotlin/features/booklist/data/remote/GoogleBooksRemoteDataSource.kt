package features.booklist.data.remote

import features.booklist.data.mapper.toDomainModel
import features.booklist.data.model.BookItem
import features.booklist.data.model.GoogleBooksResponse
import features.booklist.domain.model.Book
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import io.ktor.utils.io.errors.IOException

enum class TrendingCategory(val query: String) {
    FICTION("subject:fiction"),
    NON_FICTION("subject:non-fiction"),
    BUSINESS("subject:business"),
    TECHNOLOGY("subject:computers"),
    SCIENCE("subject:science"),
    ROMANCE("subject:romance"),
    MYSTERY("subject:mystery"),
    BIOGRAPHY("subject:biography+autobiography")
}

class GoogleBooksRemoteDataSource(
    private val httpClient: GoogleBooksHttpClient
) {
    suspend fun searchBooks(
        query: String, 
        maxResults: Int = 20,
        startIndex: Int = 0
    ): GoogleBooksResponse {
        return withContext(Dispatchers.Default) {
            try {
                val response = httpClient.client.get("volumes") {
                    parameter("q", query)
                    parameter("maxResults", maxResults)
                    parameter("startIndex", startIndex)
                    // Access API key via the getter method
                    parameter("key", GoogleBooksHttpClient.API_KEY)
                    // Explicitly request all fields we need
                    parameter("fields", "kind,totalItems,items(id,volumeInfo,saleInfo,accessInfo,searchInfo)")
                }
                
                checkResponse(response)
            } catch (e: Exception) {
                handleException(e, "searchBooks")
            }
        }
    }

    suspend fun getTrendingBooks(
        category: TrendingCategory = TrendingCategory.FICTION,
        maxResults: Int = 20,
        startIndex: Int = 0
    ): GoogleBooksResponse {
        return withContext(Dispatchers.Default) {
            try {
                val response = httpClient.client.get("volumes") {
                    parameter("q", category.query)
                    parameter("orderBy", "relevance") // Fixed to relevance (previously THIS_MONTH)
                    parameter("maxResults", maxResults)
                    parameter("startIndex", startIndex)
                    // Explicitly request all fields we need
                    parameter("fields", "kind,totalItems,items(id,volumeInfo,saleInfo,accessInfo,searchInfo)")
                    
                    // Standard filters
                    parameter("filter", "ebooks") // Only eBooks
                    parameter("langRestrict", "en") // English books only
                    parameter("printType", "books") // Only books, no magazines
                    
                    // Access API key via the getter method
                    parameter("key", GoogleBooksHttpClient.API_KEY)
                }

                checkResponse(response)
            } catch (e: Exception) {
                handleException(e, "getTrendingBooks")
            }
        }
    }
    
    /**
     * Fetches a specific book by its ID
     */
    suspend fun getBookById(bookId: String): Book {
        return withContext(Dispatchers.Default) {
            try {
                val response = httpClient.client.get("volumes/$bookId") {
                    // Access API key via the getter method
                    parameter("key", GoogleBooksHttpClient.API_KEY)
                }
                
                if (response.status.isSuccess()) {
                    val bookItem = response.body<BookItem>()
                    bookItem.toDomainModel()
                } else {
                    val errorBody = response.bodyAsText()
                    println("API Error (Status ${response.status.value}): $errorBody")
                    throw IOException("API Error: ${response.status.value} - $errorBody")
                }
            } catch (e: Exception) {
                handleException(e, "getBookById")
            }
        }
    }
    
    private suspend fun checkResponse(response: HttpResponse): GoogleBooksResponse {
        if (response.status.isSuccess()) {
            try {
                return response.body()
            } catch (e: Exception) {
                val responseText = response.bodyAsText()
                println("Response parsing error. Raw response: $responseText")
                throw Exception("Failed to parse response: ${e.message}")
            }
        } else {
            val errorBody = response.bodyAsText()
            println("API Error (Status ${response.status.value}): $errorBody")
            throw IOException("API Error: ${response.status.value} - $errorBody")
        }
    }
    
    private fun handleException(e: Exception, method: String): Nothing {
        val errorMsg = e.message ?: "Unknown error"
        println("Error in $method: $errorMsg")
        throw e
    }
} 
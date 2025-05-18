package features.booklist.data.remote

import features.booklist.data.model.GoogleBooksResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

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

enum class TrendingTimeRange(val maxResults: Int, val orderBy: String) {
    THIS_WEEK(20, "newest"),
    THIS_MONTH(30, "newest"),
    THIS_YEAR(40, "relevance")
}

class GoogleBooksRemoteDataSource(
    private val httpClient: GoogleBooksHttpClient
) {
    suspend fun searchBooks(query: String, maxResults: Int = 40): GoogleBooksResponse {
        return httpClient.client.get("volumes") {
            parameter("q", query)
            parameter("maxResults", maxResults)
            parameter("key", GoogleBooksHttpClient.API_KEY)
        }.body()
    }

    suspend fun getTrendingBooks(
        category: TrendingCategory = TrendingCategory.FICTION,
        timeRange: TrendingTimeRange = TrendingTimeRange.THIS_MONTH
    ): GoogleBooksResponse {
        return httpClient.client.get("volumes") {
            parameter("q", category.query)
            parameter("orderBy", timeRange.orderBy)
            parameter("maxResults", timeRange.maxResults)
            // Add date filter based on timeRange
            when (timeRange) {
                TrendingTimeRange.THIS_WEEK -> {
                    parameter("filter", "partial") // Only books with preview available
                    parameter("langRestrict", "en") // English books only
                    parameter("printType", "books") // Only books, no magazines
                    parameter("download", "epub") // Only books available as eBooks
                }
                TrendingTimeRange.THIS_MONTH -> {
                    parameter("filter", "ebooks") // Only eBooks
                    parameter("langRestrict", "en")
                    parameter("printType", "books")
                }
                TrendingTimeRange.THIS_YEAR -> {
                    parameter("langRestrict", "en")
                    parameter("printType", "books")
                }
            }
            parameter("key", GoogleBooksHttpClient.API_KEY)
        }.body()
    }
} 
package booklist.data.remoteDS

import booklist.data.model.TrendingBooksResponse
import booklist.data.model.WorkDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*


class BooksRemoteDataSource(private val httpClient: HttpClient) {
    suspend fun getBooksTrendingToday(): List<WorkDTO> {
        val response = httpClient.get() {
            url {
                path("trending/daily")
            }
        }
        return response.body<TrendingBooksResponse>().books
    }

    suspend fun getBooksTrendingThisWeek(): List<WorkDTO> {
        val response = httpClient.get() {
            url {
                path("trending/weekly")
            }
        }
        return response.body<TrendingBooksResponse>().books
    }

    suspend fun getBooksTrendingThisMonth(): List<WorkDTO> {
        val response = httpClient.get() {
            url {
                path("trending/monthly")
            }
        }
        return response.body<TrendingBooksResponse>().books
    }

    suspend fun getBooksTrendingThisYear(): List<WorkDTO> {
        val response = httpClient.get() {
            url {
                path("trending/yearly")
            }
        }
        return response.body<TrendingBooksResponse>().books
    }
}


package features.booklist.data.repository

import features.booklist.data.mapper.toDomainModel
import features.booklist.data.remote.GoogleBooksRemoteDataSource
import features.booklist.data.remote.TrendingCategory
import features.booklist.data.remote.TrendingTimeRange
import features.booklist.domain.model.Book
import features.booklist.domain.repository.BooksRepository
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import core.domain.DataState.Loading
import core.domain.AppError
import core.domain.DataState

class BooksRepositoryImpl(
    private val remoteDataSource: GoogleBooksRemoteDataSource
) : BooksRepository {

    override fun getTrendingBooks(
        category: TrendingCategory,
        timeRange: TrendingTimeRange
    ): Flow<DataState<List<Book>>> = flow {
        emit(DataState.Loading)
        try {
            val response = remoteDataSource.getTrendingBooks(category, timeRange)
            emit(DataState.Success(response.toDomainModel()))
        } catch (e: HttpRequestTimeoutException) {
            emit(DataState.Error(AppError.Exception("Request timed out. Please check your internet connection.")))
        } catch (e: ClientRequestException) {
            emit(DataState.Error(AppError.Business(e.message)))
        } catch (e: ServerResponseException) {
            emit(DataState.Error(AppError.Server(e.message)))
        } catch (e: Exception) {
            emit(DataState.Error(AppError.Exception(e.message ?: "Unknown error occurred")))
        }
    }

    override fun searchBooks(query: String): Flow<DataState<List<Book>>> = flow {
        emit(DataState.Loading)
        try {
            val response = remoteDataSource.searchBooks(query)
            emit(DataState.Success(response.toDomainModel()))
        } catch (e: HttpRequestTimeoutException) {
            emit(DataState.Error(AppError.Exception("Request timed out. Please check your internet connection.")))
        } catch (e: ClientRequestException) {
            emit(DataState.Error(AppError.Business(e.message)))
        } catch (e: ServerResponseException) {
            emit(DataState.Error(AppError.Server(e.message)))
        } catch (e: Exception) {
            emit(DataState.Error(AppError.Exception(e.message ?: "Unknown error occurred")))
        }
    }
}
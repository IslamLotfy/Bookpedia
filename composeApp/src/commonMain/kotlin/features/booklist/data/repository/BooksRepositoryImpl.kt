package features.booklist.data.repository

import features.booklist.data.mapper.toDomainModel
import features.booklist.data.remote.GoogleBooksRemoteDataSource
import features.booklist.data.remote.TrendingCategory
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
        page: Int,
        pageSize: Int
    ): Flow<DataState<List<Book>>> = flow {
        emit(DataState.Loading)
        try {
            // Convert page to startIndex (0-based pagination)
            val startIndex = page * pageSize
            
            val response = remoteDataSource.getTrendingBooks(
                category = category,
                maxResults = pageSize,
                startIndex = startIndex
            )
            
            // Handle empty items array gracefully
            val books = response.items?.map { it.toDomainModel() } ?: emptyList()
            emit(DataState.Success(books))
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

    override fun searchBooks(
        query: String,
        page: Int,
        pageSize: Int
    ): Flow<DataState<List<Book>>> = flow {
        emit(DataState.Loading)
        try {
            // Convert page to startIndex (0-based pagination)
            val startIndex = page * pageSize
            
            val response = remoteDataSource.searchBooks(
                query = query,
                maxResults = pageSize,
                startIndex = startIndex
            )
            
            // Handle empty items array gracefully
            val books = response.items?.map { it.toDomainModel() } ?: emptyList()
            emit(DataState.Success(books))
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
    
    /**
     * Gets a specific book by its ID
     * @param bookId The ID of the book to fetch
     * @return The book if found
     * @throws Exception if the book could not be fetched
     */
    override suspend fun getBookById(bookId: String): Book {
        return remoteDataSource.getBookById(bookId)
    }
}
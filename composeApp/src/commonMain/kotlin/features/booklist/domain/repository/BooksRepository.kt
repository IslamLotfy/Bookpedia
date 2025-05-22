package features.booklist.domain.repository

import core.domain.DataState
import features.booklist.data.remote.TrendingCategory
import features.booklist.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    fun getTrendingBooks(
        category: TrendingCategory = TrendingCategory.FICTION,
        page: Int = 0,
        pageSize: Int = 20
    ): Flow<DataState<List<Book>>>
    
    fun searchBooks(
        query: String,
        page: Int = 0,
        pageSize: Int = 20
    ): Flow<DataState<List<Book>>>
    
    /**
     * Gets a specific book by its ID
     * @param bookId The ID of the book to fetch
     * @return DataState containing the book if found or an error
     */
    suspend fun getBookById(bookId: String): Book
}
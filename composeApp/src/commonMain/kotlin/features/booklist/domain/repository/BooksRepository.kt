package features.booklist.domain.repository

import core.domain.DataState
import features.booklist.data.remote.TrendingCategory
import features.booklist.data.remote.TrendingTimeRange
import features.booklist.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    fun getTrendingBooks(
        category: TrendingCategory = TrendingCategory.FICTION,
        timeRange: TrendingTimeRange = TrendingTimeRange.THIS_MONTH
    ): Flow<DataState<List<Book>>>
    
    fun searchBooks(query: String): Flow<DataState<List<Book>>>
}
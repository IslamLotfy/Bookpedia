package features.booklist.domain.usecase

import features.booklist.data.remote.TrendingCategory
import features.booklist.data.remote.TrendingTimeRange
import features.booklist.domain.model.Book
import features.booklist.domain.repository.BooksRepository
import core.domain.DataState
import kotlinx.coroutines.flow.Flow

class GetTrendingBooksUseCase(
    private val repository: BooksRepository
) {
    operator fun invoke(
        category: TrendingCategory = TrendingCategory.FICTION,
        timeRange: TrendingTimeRange = TrendingTimeRange.THIS_MONTH
    ): Flow<DataState<List<Book>>> {
        return repository.getTrendingBooks(category, timeRange)
    }
} 
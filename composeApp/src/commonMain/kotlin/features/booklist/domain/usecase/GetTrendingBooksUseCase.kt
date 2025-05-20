package features.booklist.domain.usecase

import features.booklist.data.remote.TrendingCategory
import features.booklist.domain.model.Book
import features.booklist.domain.repository.BooksRepository
import core.domain.DataState
import kotlinx.coroutines.flow.Flow

class GetTrendingBooksUseCase(
    private val repository: BooksRepository
) {
    operator fun invoke(
        category: TrendingCategory = TrendingCategory.FICTION,
        page: Int = 0,
        pageSize: Int = 20
    ): Flow<DataState<List<Book>>> {
        return repository.getTrendingBooks(
            category = category,
            page = page,
            pageSize = pageSize
        )
    }
} 
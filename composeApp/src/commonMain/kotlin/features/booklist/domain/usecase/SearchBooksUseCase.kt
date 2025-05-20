package features.booklist.domain.usecase

import features.booklist.domain.model.Book
import features.booklist.domain.repository.BooksRepository
import core.domain.DataState
import kotlinx.coroutines.flow.Flow

class SearchBooksUseCase(
    private val repository: BooksRepository
) {
    operator fun invoke(
        query: String,
        page: Int = 0,
        pageSize: Int = 20
    ): Flow<DataState<List<Book>>> {
        return repository.searchBooks(
            query = query,
            page = page,
            pageSize = pageSize
        )
    }
} 
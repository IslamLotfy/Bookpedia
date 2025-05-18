package features.booklist.domain.usecase

import features.booklist.domain.model.Book
import features.booklist.domain.repository.BooksRepository
import core.domain.DataState
import kotlinx.coroutines.flow.Flow

class SearchBooksUseCase(
    private val repository: BooksRepository
) {
    operator fun invoke(query: String): Flow<DataState<List<Book>>> {
        return repository.searchBooks(query)
    }
} 
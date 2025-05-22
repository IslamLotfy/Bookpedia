package features.booklist.domain.usecase

import features.booklist.domain.model.Book
import features.booklist.domain.repository.BooksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Use case for fetching a book by its ID
 */
class GetBookByIdUseCase(
    private val booksRepository: BooksRepository
) {
    /**
     * Retrieves a book by its ID
     * @param bookId The ID of the book to fetch
     * @return The book if found
     * @throws Exception if the book could not be fetched
     */
    suspend operator fun invoke(bookId: String): Book {
        return withContext(Dispatchers.Default) {
            booksRepository.getBookById(bookId)
        }
    }
} 
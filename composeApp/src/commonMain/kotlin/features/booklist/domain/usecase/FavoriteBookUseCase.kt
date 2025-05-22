package features.booklist.domain.usecase

import features.booklist.domain.model.Book
import features.booklist.domain.repository.FavoriteBookRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteBooksUseCase(private val favoriteBookRepository: FavoriteBookRepository) {
    operator fun invoke(): Flow<List<Book>> {
        return favoriteBookRepository.getAllFavoriteBooks()
    }
}

class IsFavoriteBookUseCase(private val favoriteBookRepository: FavoriteBookRepository) {
    operator fun invoke(bookId: String): Flow<Boolean> {
        return favoriteBookRepository.isFavoriteBook(bookId)
    }
}

class ToggleFavoriteBookUseCase(private val favoriteBookRepository: FavoriteBookRepository) {
    suspend operator fun invoke(book: Book): Boolean {
        return favoriteBookRepository.toggleFavoriteStatus(book)
    }
} 
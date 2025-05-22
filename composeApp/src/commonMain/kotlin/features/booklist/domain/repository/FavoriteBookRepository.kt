package features.booklist.domain.repository

import features.booklist.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface FavoriteBookRepository {
    fun getAllFavoriteBooks(): Flow<List<Book>>
    fun isFavoriteBook(bookId: String): Flow<Boolean>
    suspend fun addFavoriteBook(book: Book)
    suspend fun removeFavoriteBook(bookId: String)
    suspend fun toggleFavoriteStatus(book: Book): Boolean
} 
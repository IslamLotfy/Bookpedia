package features.booklist.data.repository

import features.booklist.data.local.FavoriteBookDao
import features.booklist.data.local.toBook
import features.booklist.data.local.toFavoriteBookEntity
import features.booklist.domain.model.Book
import features.booklist.domain.repository.FavoriteBookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteBookRepositoryImpl(
    private val favoriteBookDao: FavoriteBookDao
) : FavoriteBookRepository {
    
    override fun getAllFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao.getAllFavoriteBooks().map { entities ->
            entities.map { it.toBook() }
        }
    }
    
    override fun isFavoriteBook(bookId: String): Flow<Boolean> {
        return favoriteBookDao.isFavoriteBook(bookId)
    }
    
    override suspend fun addFavoriteBook(book: Book) {
        favoriteBookDao.insertFavoriteBook(book.toFavoriteBookEntity())
    }
    
    override suspend fun removeFavoriteBook(bookId: String) {
        favoriteBookDao.deleteFavoriteBookById(bookId)
    }
    
    override suspend fun toggleFavoriteStatus(book: Book): Boolean {
        val isFavorite = favoriteBookDao.getFavoriteBook(book.id) != null
        
        if (isFavorite) {
            favoriteBookDao.deleteFavoriteBookById(book.id)
            return false
        } else {
            favoriteBookDao.insertFavoriteBook(book.toFavoriteBookEntity())
            return true
        }
    }
} 
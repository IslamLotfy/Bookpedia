package features.booklist.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {
    @Query("SELECT * FROM favorite_books ORDER BY timestamp DESC")
    fun getAllFavoriteBooks(): Flow<List<FavoriteBookEntity>>
    
    @Query("SELECT * FROM favorite_books WHERE id = :bookId")
    suspend fun getFavoriteBook(bookId: String): FavoriteBookEntity?
    
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_books WHERE id = :bookId)")
    fun isFavoriteBook(bookId: String): Flow<Boolean>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteBook(favoriteBook: FavoriteBookEntity)
    
    @Delete
    suspend fun deleteFavoriteBook(favoriteBook: FavoriteBookEntity)
    
    @Query("DELETE FROM favorite_books WHERE id = :bookId")
    suspend fun deleteFavoriteBookById(bookId: String)
} 
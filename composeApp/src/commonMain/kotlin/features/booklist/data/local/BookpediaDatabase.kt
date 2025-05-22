package features.booklist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteBookEntity::class],
    version = 1,
    exportSchema = true
)
abstract class BookpediaDatabase : RoomDatabase() {
    abstract fun favoriteBookDao(): FavoriteBookDao
} 
package di

import androidx.room.Room
import androidx.room.RoomDatabase
import features.booklist.data.local.BookpediaDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(context: Any): Module = module {
    // iOS-specific dependencies
}

actual fun getBookpediaDatabase(context: Any): BookpediaDatabase {
    return Room.databaseBuilder(
        context as RoomDatabase.Builder.Context,
        BookpediaDatabase::class.java,
        "bookpedia-database"
    ).build()
} 
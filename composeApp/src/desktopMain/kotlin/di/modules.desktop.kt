package di

import androidx.room.Room
import androidx.room.RoomDatabase
import features.booklist.data.local.BookpediaDatabase
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(context: Any): Module = module {
    // Desktop-specific dependencies
}

actual fun getBookpediaDatabase(context: Any): BookpediaDatabase {
    // Basic implementation using local file system (would require additional setup for desktop)
    return Room.databaseBuilder(
        context as RoomDatabase.Builder.Context,
        BookpediaDatabase::class.java,
        "bookpedia-database"
    ).build()
} 
package di

import android.content.Context
import androidx.room.Room
import features.booklist.data.local.BookpediaDatabase
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.core.module.Module

actual fun platformModule(context: Any): Module = module {
    single<HttpClientEngine> {
        OkHttp.create()
    }
    
    // Provide the Android Context
    single<Any> { androidContext() }
}

actual fun getBookpediaDatabase(context: Any): BookpediaDatabase {
    return Room.databaseBuilder(
        context as Context,
        BookpediaDatabase::class.java,
        "bookpedia-database"
    ).build()
}
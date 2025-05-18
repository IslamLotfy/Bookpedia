package di

import features.booklist.di.bookModule
import org.koin.dsl.module

val appModule = module {
    includes(bookModule)
} 
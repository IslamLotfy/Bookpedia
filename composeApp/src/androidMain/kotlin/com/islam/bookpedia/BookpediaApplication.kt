package com.islam.bookpedia

import android.app.Application
import di.initKoin
import org.koin.android.ext.koin.androidContext

class BookpediaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        
        initKoin(this) {
            androidContext(this@BookpediaApplication)
        }
    }
}
package com.islam.bookpedia

import android.app.Application
import di.initKoin

class BookpediaApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
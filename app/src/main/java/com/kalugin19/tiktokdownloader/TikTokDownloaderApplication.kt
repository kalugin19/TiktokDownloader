package com.kalugin19.tiktokdownloader

import android.app.Application

class TikTokDownloaderApplication : Application() {

    companion object {
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}
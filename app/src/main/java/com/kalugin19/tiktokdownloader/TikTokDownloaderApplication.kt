package com.kalugin19.tiktokdownloader

import android.app.Application
import android.app.DownloadManager
import android.content.ClipboardManager
import android.content.Context

class TikTokDownloaderApplication: Application() {

    val clipboardManager by lazy {
        getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    }

    val downloadManager: DownloadManager by lazy {
        getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }
}
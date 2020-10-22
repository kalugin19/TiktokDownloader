package com.kalugin19.tiktokdownloader.ui.main

import android.app.DownloadManager

interface TikTokApi {
    suspend fun prepareDownloadingRequest(url: String) : DownloadManager.Request
}
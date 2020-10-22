package com.kalugin19.tiktokdownloader.ui.main

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment


class TikTokApiImpl : TikTokApi {

    override suspend fun prepareDownloadingRequest(url: String): DownloadManager.Request {
        val pair = TikTokUrlParser.parse(url)
            .run {
                title to this.url
            }
        val title = pair.first
        val downloadUrl = pair.second
        return startDownloading(title, downloadUrl)
    }

    private fun startDownloading(title: String, downloadUrl: String): DownloadManager.Request {
        return DownloadManager.Request(Uri.parse(downloadUrl))
            .setTitle(title)
            .setDescription("Downloading...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.getExternalStorageState(), title
            )
    }

}
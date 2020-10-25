package com.kalugin19.tiktokdownloader.util

import android.os.Environment
import com.kalugin19.tiktokdownloader.TikTokDownloaderApplication
import com.kalugin19.tiktokdownloader.api.TikTokApi
import com.kalugin19.tiktokdownloader.api.TikTokApiImpl
import com.kalugin19.tiktokdownloader.api.TikTokParser
import com.kalugin19.tiktokdownloader.api.TikTokParserImpl
import com.kalugin19.tiktokdownloader.repository.TikTokRepository
import java.io.File

object ServiceLocator {

    val application = TikTokDownloaderApplication.application

    private val downloadsDirectory: File by lazy {
        application.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!
    }

    private val tikTokParser: TikTokParser by lazy {
        TikTokParserImpl()
    }

    private val tikTokApi: TikTokApi by lazy {
        TikTokApiImpl(
            dir = downloadsDirectory,
            tikTokParser = tikTokParser
        )
    }

    val tikTokRepository by lazy {
        TikTokRepository(tikTokApi)
    }
}
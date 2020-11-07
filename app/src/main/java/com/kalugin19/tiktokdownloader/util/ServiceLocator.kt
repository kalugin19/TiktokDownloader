package com.kalugin19.tiktokdownloader.util

import android.annotation.SuppressLint
import android.webkit.WebView
import com.kalugin19.tiktokdownloader.TikTokDownloaderApplication
import com.kalugin19.tiktokdownloader.api.TikTokApi
import com.kalugin19.tiktokdownloader.api.TikTokApi2
import com.kalugin19.tiktokdownloader.repository.TikTokRepository
import com.kalugin19.tiktokdownloader.repository.TikTokRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

@SuppressLint("SetJavaScriptEnabled")
object ServiceLocator {

    private const val TIK_TOK_VIDEOS = "tik_tok_videos"

    private val application = TikTokDownloaderApplication.application

    private val cacheDirectory by lazy {
        application.cacheDir
    }

    private val tikTokVideosFileDir by lazy {
        val dir = File(cacheDirectory, TIK_TOK_VIDEOS)
        dir.mkdir()
        dir
    }

    val tikTokRepository: TikTokRepository by lazy {
        TikTokRepositoryImpl(tikTokApi, )
    }

    private val tikTokApi: TikTokApi by lazy {
        TikTokApi2(tikTokVideosFileDir) { arg1, arg2 ->
            loadTikTokVideoUrl(arg1, arg2)
        }
    }

    private val client by lazy {
        CustomWebViewClient()
    }

    private val webView by lazy {
        WebView(application).apply {
            webViewClient = client
            settings.javaScriptEnabled = true
        }
    }


    private fun loadTikTokVideoUrl(redirectedUrl: String, func: (Result<String>) -> Unit) = run {
        GlobalScope.launch(Dispatchers.Main) {
            client.func = {
                func(it)
                client.func = {}
            }
            webView.loadUrl(redirectedUrl)
        }
    }
}

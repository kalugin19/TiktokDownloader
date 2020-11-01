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

@SuppressLint("SetJavaScriptEnabled")
object ServiceLocator {

    private val application = TikTokDownloaderApplication.application

    val tikTokRepository: TikTokRepository by lazy {
        TikTokRepositoryImpl(tikTokApi)
    }

    private val tikTokApi: TikTokApi by lazy {
        TikTokApi2 { arg1, arg2 ->
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


    private fun loadTikTokVideoUrl(redirectedUrl: String, func: (String) -> Unit) = run {
        GlobalScope.launch(Dispatchers.Main) {
            client.func = {
                func(it)
            }
            webView.loadUrl(redirectedUrl)
        }
    }
}

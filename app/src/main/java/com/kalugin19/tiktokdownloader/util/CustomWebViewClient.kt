package com.kalugin19.tiktokdownloader.util

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebViewClient(var func: (String) -> Unit = {}) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url
        url?.let {
            func(it.toString())
        }
        return super.shouldOverrideUrlLoading(view, request)
    }

}
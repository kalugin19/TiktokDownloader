package com.kalugin19.tiktokdownloader.util

import android.webkit.*

class CustomWebViewClient(var func: (Result<String>) -> Unit = {}) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url
        url?.let {
            val result = Result.success(it.toString())
            func(result)
        }
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        val exc = error
                ?.description
                ?.let { RuntimeException(it.toString()) }
                ?: RuntimeException("Stub!")
        val result = Result.failure<String>(exc)
        func(result)
        super.onReceivedError(view, request, error)
    }

    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?
    ) {
        val exc = errorResponse
            ?.reasonPhrase
            ?.let { RuntimeException(it) }
            ?: RuntimeException("Stub!")
        val result = Result.failure<String>(exc)
        func(result)
        super.onReceivedHttpError(view, request, errorResponse)
    }

}
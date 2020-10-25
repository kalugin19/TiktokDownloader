package com.kalugin19.tiktokdownloader.util

import android.app.Application
import android.app.DownloadManager
import android.content.ClipboardManager
import android.content.Context


private const val HTTP = "http://"
private const val HTTPS = "https://"
private const val TIK_TOK = "tiktok.com"

val String.isHttpUrl: Boolean
    get() = startsWith(HTTP) || startsWith(HTTPS)

val String.isTikTokUrl: Boolean
    get() = contains(TIK_TOK)


fun String.removeBodyTags(): String {
    return replace(BODY_HTML_TAG, "")
            .replace(BODY_END_HTML_TAG, "")
}

val Context.downloadManager: DownloadManager
    get() = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager


fun Context.getText(): String? {
    val clipboardManager = getSystemService(Application.CLIPBOARD_SERVICE) as ClipboardManager
    return clipboardManager
            .primaryClip
            ?.getItemAt(0)
            ?.coerceToText(this)
            ?.toString()

}



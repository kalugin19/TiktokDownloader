package com.kalugin19.tiktokdownloader.util

import android.app.Application
import android.content.ClipboardManager
import android.content.Context


fun Context.getText(): String? {
    val clipboardManager = getSystemService(Application.CLIPBOARD_SERVICE) as ClipboardManager
    return clipboardManager
            .primaryClip
            ?.getItemAt(0)
            ?.coerceToText(this)
            ?.toString()

}



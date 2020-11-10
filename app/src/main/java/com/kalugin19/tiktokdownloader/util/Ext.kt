package com.kalugin19.tiktokdownloader.util

import android.app.Application
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.io.File


fun Context.getText(): String? {
    val clipboardManager = getSystemService(Application.CLIPBOARD_SERVICE) as ClipboardManager
    return clipboardManager
            .primaryClip
            ?.getItemAt(0)
            ?.coerceToText(this)
            ?.toString()
}

fun Context.saveVideo(title: String, videoFile: File): Uri? {
    val values = ContentValues(3)
    values.put(MediaStore.Video.Media.TITLE, title)
    values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
    values.put(MediaStore.Video.Media.DATA, videoFile.absolutePath)
    return contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
}

package com.kalugin19.tiktokdownloader.util

import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.material.textfield.TextInputLayout
import com.kalugin19.tiktokdownloader.ui.videoplayer.BoundPlayerLifecycleHandler
import java.io.File

@BindingAdapter(value = ["app:endIconVisibility"])
fun TextInputLayout.showEndIconVisibility(endIconVisibility: Boolean) {
    isEndIconVisible = endIconVisibility
}

@BindingAdapter(value = ["app:endBtnListener"])
inline fun TextInputLayout.endBtnListener(crossinline func: () -> Unit) {
    setEndIconOnClickListener {
        func()
    }
}

@BindingAdapter(value = ["app:error_text"])
fun TextInputLayout.errorText(text: String?) {
    if (text.isNullOrBlank()) {
        isErrorEnabled = false
    }
    error = text
}

@BindingAdapter(value = ["app:videoFile", "app:lifecycle"], requireAll = true)
fun PlayerView.video(file: File?, lifecycle: LifecycleOwner) {
    file?.let {
        BoundPlayerLifecycleHandler(
            file,
            this,
            lifecycle
        )
    }
}

@BindingAdapter(value = ["app:show"])
fun ContentLoadingProgressBar.showProgress(isShow: Boolean) {
    isActivated = isShow
    if (isShow) show() else hide()
}

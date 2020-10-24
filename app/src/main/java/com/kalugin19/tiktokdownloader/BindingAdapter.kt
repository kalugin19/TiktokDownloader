package com.kalugin19.tiktokdownloader

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter(value = ["app:endIconVisibility"])
fun TextInputLayout.showEndIconVisibility(endIconVisibility: Boolean){
    isEndIconVisible = endIconVisibility
}

@BindingAdapter(value = ["app:endBtnListener"])
inline fun TextInputLayout.endBtnListener(crossinline func: () -> Unit){
    setEndIconOnClickListener {
        func()
    }
}
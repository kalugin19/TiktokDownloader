package com.kalugin19.tiktokdownloader.data

class ApiResult<T> {

    private var result: Result<T>? = null

    var progress: Int = 0

    val isSuccess: Boolean get() = result?.isSuccess ?: false

    val isFailure: Boolean get() = result?.isFailure ?: false

    val isProgress: Boolean get() = result == null

    fun setResult(result: Result<T>){
        this.result = result
    }

    fun getExceptionOrNull() = result?.exceptionOrNull()

    fun getOrNull() = result?.getOrNull()

}
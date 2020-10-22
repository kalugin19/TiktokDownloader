package com.kalugin19.tiktokdownloader


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
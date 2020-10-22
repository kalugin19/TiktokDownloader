package com.kalugin19.tiktokdownloader

const val TIK_TOK_API: String = "https://api2-16-h2.musical.ly/aweme/v1/aweme/detail/"

private const val COOKIE_KEY = "Cookie"
private const val COOKIE_VALUE = "1"
private const val USER_AGENT_KEY = "User-Agent"
private const val USER_AGENT_VALUE = "1"
private const val ACCEPT_KEY = "Accept"
private const val ACCEPT_VALUE = "application/json"
private const val HOST_KEY = "Host"
private const val HOST_VALUE = "api2-16-h2.musical.ly"
private const val CONNECTION_KEY = "Connection"
private const val CONNECTION_VALUE = "keep-alive"

const val ID = "aweme_id"
const val DETAIL = "aweme_detail"
const val VIDEO_JSON_KEY = "video"
const val PLAY_ADDRESS = "play_addr"
const val URLS_LIST = "url_list"


const val BODY_HTML_TAG = "<body>"
const val BODY_END_HTML_TAG = "</body>"

const val MP4_EXT = ".mp4"

val tikTokDownloadHeader by lazy {
    mutableMapOf(
        COOKIE_KEY to COOKIE_VALUE,
        USER_AGENT_KEY to USER_AGENT_VALUE,
        ACCEPT_KEY to ACCEPT_VALUE,
        HOST_KEY to HOST_VALUE,
        CONNECTION_KEY to CONNECTION_VALUE,
    )
}


package com.kalugin19.tiktokdownloader.ui.videoplayer

import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.REPEAT_MODE_ALL
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util

import java.io.File

class BoundPlayerLifecycleHandler(
    private val videoFile: File,
    private val playerView: PlayerView,
    lifecycleOwner: LifecycleOwner
) : LifecycleObserver {

    private var simplePlayer: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun initPlayerAfter24Api() {
        if (Util.SDK_INT >= 24) {
            playerView.initPlayer(videoFile)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun initPlayerBefore24Api() {
        if (Util.SDK_INT < 24) {
            playerView.initPlayer(videoFile)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun releasePlayerAfter24Api() {
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun releasePlayerBefore24Api() {
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        if (simplePlayer != null) {
            playWhenReady = simplePlayer?.playWhenReady ?: true
            playbackPosition = simplePlayer?.currentPosition ?: 0
            currentWindow = simplePlayer?.currentWindowIndex ?: 0
            simplePlayer?.release()
            simplePlayer = null
        }
    }

    private fun PlayerView.initPlayer(file: File) {
        simplePlayer = SimpleExoPlayer.Builder(playerView.context)
            .setUseLazyPreparation(true)
            .build()
        player = simplePlayer

        val mediaItem: MediaItem = MediaItem.fromUri(file.toUri())
        simplePlayer?.volume = 0f
        simplePlayer?.setMediaItem(mediaItem)
        this.useController = false
        simplePlayer?.playWhenReady = playWhenReady
        simplePlayer?.seekTo(currentWindow, playbackPosition)
        simplePlayer?.repeatMode = REPEAT_MODE_ALL
        simplePlayer?.prepare()
    }
}
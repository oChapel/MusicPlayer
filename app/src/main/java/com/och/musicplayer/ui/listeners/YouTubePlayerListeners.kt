package com.och.musicplayer.ui.listeners

import com.google.android.youtube.player.YouTubePlayer

interface YouTubePlayerListeners : YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaylistEventListener,
    YouTubePlayer.PlayerStateChangeListener, YouTubePlayer.PlaybackEventListener {

    override fun onPlaylistEnded() {}

    override fun onLoading() {}

    override fun onAdStarted() {}

    override fun onVideoStarted() {}

    override fun onVideoEnded() {}

    override fun onSeekTo(i: Int) {}

    override fun onBuffering(p0: Boolean) {}

    companion object {
        const val REQUEST_DIALOG_INT = 100
    }
}

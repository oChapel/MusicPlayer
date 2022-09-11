package com.och.musicplayer.ui.player

import com.google.android.youtube.player.YouTubePlayer

interface YouTubePlayerListeners : YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaylistEventListener,
    YouTubePlayer.PlayerStateChangeListener, YouTubePlayer.PlaybackEventListener {

    override fun onPlaylistEnded() {}

    override fun onLoading() {}

    override fun onAdStarted() {}

    override fun onVideoStarted() {}

    override fun onVideoEnded() {}

    override fun onError(p0: YouTubePlayer.ErrorReason?) { }

    override fun onBuffering(p0: Boolean) {}
}

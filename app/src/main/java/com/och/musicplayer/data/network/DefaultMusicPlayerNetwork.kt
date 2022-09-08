package com.och.musicplayer.data.network

import com.och.musicplayer.data.dto.PlaylistSchema

class DefaultMusicPlayerNetwork(private val api: YoutubeApi) : MusicPlayerNetwork {

    override suspend fun getTop100Playlist(count: Int): PlaylistSchema {
        return api.getPlaylist(
            YoutubeApi.PART_SNIPPET,
            YoutubeApi.TOP100_PLAYLIST,
            count
        )
    }

    override suspend fun getTop10Playlist(count: Int): PlaylistSchema {
        return api.getPlaylist(
            YoutubeApi.PART_SNIPPET,
            YoutubeApi.TOP10_PLAYLIST,
            count
        )
    }
}
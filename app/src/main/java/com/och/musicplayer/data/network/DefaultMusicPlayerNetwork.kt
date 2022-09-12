package com.och.musicplayer.data.network

import com.och.musicplayer.BuildConfig
import com.och.musicplayer.data.dto.PlaylistSchema
import com.och.musicplayer.data.dto.SearchSchema

class DefaultMusicPlayerNetwork(private val api: YouTubeApi) : MusicPlayerNetwork {

    override suspend fun getTop100Playlist(count: Int): PlaylistSchema {
        return api.getPlaylist(
            YouTubeApi.PART_SNIPPET,
            YouTubeApi.TOP100_PLAYLIST,
            count,
            BuildConfig.YOUTUBE_API_KEY,
        )
    }

    override suspend fun getTop10Playlist(count: Int): PlaylistSchema {
        return api.getPlaylist(
            YouTubeApi.PART_SNIPPET,
            YouTubeApi.TOP10_PLAYLIST,
            count,
            BuildConfig.YOUTUBE_API_KEY,
        )
    }

    override suspend fun searchForVideo(q: String, count: Int): SearchSchema {
        return api.searchForVideo(
            YouTubeApi.PART_SNIPPET,
            count,
            q,
            YouTubeApi.TYPE_VIDEO,
            BuildConfig.YOUTUBE_API_KEY
        )
    }
}
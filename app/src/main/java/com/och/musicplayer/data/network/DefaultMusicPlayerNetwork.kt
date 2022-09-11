package com.och.musicplayer.data.network

import com.och.musicplayer.BuildConfig
import com.och.musicplayer.data.dto.PlaylistSchema
import com.och.musicplayer.data.dto.SearchResultSchema

class DefaultMusicPlayerNetwork(private val api: YoutubeApi) : MusicPlayerNetwork {

    override suspend fun getTop100Playlist(count: Int): PlaylistSchema {
        return api.getPlaylist(
            YoutubeApi.PART_SNIPPET,
            YoutubeApi.TOP100_PLAYLIST,
            count,
            BuildConfig.YOUTUBE_API_KEY,
        )
    }

    override suspend fun getTop10Playlist(count: Int): PlaylistSchema {
        return api.getPlaylist(
            YoutubeApi.PART_SNIPPET,
            YoutubeApi.TOP10_PLAYLIST,
            count,
            BuildConfig.YOUTUBE_API_KEY,
        )
    }

    override suspend fun searchForVideo(q: String, count: Int): SearchResultSchema {
        return api.searchForVideo(
            YoutubeApi.PART_SNIPPET,
            count,
            q,
            YoutubeApi.TYPE_VIDEO,
            BuildConfig.YOUTUBE_API_KEY
        )
    }
}
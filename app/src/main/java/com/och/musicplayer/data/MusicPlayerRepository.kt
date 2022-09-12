package com.och.musicplayer.data

import com.och.musicplayer.data.dto.PlaylistSchema
import com.och.musicplayer.data.dto.SearchSchema

interface MusicPlayerRepository {

    suspend fun getTop100Playlist(): PlaylistSchema

    suspend fun getTop10Playlist(): PlaylistSchema

    suspend fun searchForVideo(q: String): SearchSchema

    companion object {
        const val TOP_10_PLAYLIST_COUNT = 15
        const val TOP_100_PLAYLIST_COUNT = 60
        const val SEARCH_PLAYLIST_COUNT = 60
    }
}

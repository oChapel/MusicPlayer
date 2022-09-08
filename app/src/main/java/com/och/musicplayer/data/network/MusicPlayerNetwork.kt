package com.och.musicplayer.data.network

import com.och.musicplayer.data.dto.PlaylistSchema

interface MusicPlayerNetwork {

    suspend fun getTop100Playlist(count: Int): PlaylistSchema
    suspend fun getTop10Playlist(count: Int): PlaylistSchema
}
package com.och.musicplayer.data

import com.och.musicplayer.data.dto.PlaylistSchema
import com.och.musicplayer.data.dto.SearchResultSchema
import com.och.musicplayer.data.network.MusicPlayerNetwork

class DefaultMusicPlayerRepository(
    private val remoteDataSource: MusicPlayerNetwork
) : MusicPlayerRepository {

    override suspend fun getTop100Playlist(): PlaylistSchema {
        return remoteDataSource.getTop100Playlist(MusicPlayerRepository.TOP_100_PLAYLIST_COUNT)
    }

    override suspend fun getTop10Playlist(): PlaylistSchema {
        return remoteDataSource.getTop10Playlist(MusicPlayerRepository.TOP_10_PLAYLIST_COUNT)
    }

    override suspend fun searchForVideo(q: String): SearchResultSchema {
        return remoteDataSource.searchForVideo(q, MusicPlayerRepository.SEARCH_PLAYLIST_COUNT)
    }
}

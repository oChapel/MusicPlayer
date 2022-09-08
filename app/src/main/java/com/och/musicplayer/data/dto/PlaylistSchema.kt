package com.och.musicplayer.data.dto

import com.och.musicplayer.data.network.YoutubeApi

data class PlaylistSchema(
    val etag: String,
    val songs: List<Song>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo
)

data class PageInfo(
    val resultsPerPage: Int,
    val totalResults: Int
)

data class Song(
    val etag: String,
    val id: String,
    val kind: String,
    val snippet: Snippet
) {

    fun getViewHolderType(): Int {
        return when (snippet.playlistId) {
            YoutubeApi.TOP10_PLAYLIST -> TOP10_SONG
            else -> TOP100_SONG
        }
    }

    companion object {
        const val TOP10_SONG = 0
        const val TOP100_SONG = 1
    }
}

data class Snippet(
    val channelId: String,
    val channelTitle: String,
    val description: String,
    val playlistId: String,
    val position: Int,
    val publishedAt: String,
    val resourceId: ResourceId,
    val thumbnails: Thumbnails,
    val title: String,
    val videoOwnerChannelId: String,
    val videoOwnerChannelTitle: String
)

data class ResourceId(
    val kind: String,
    val videoId: String
)

data class Thumbnails(
    val default: Default,
    val high: High,
    val maxres: Maxres,
    val medium: Medium,
    val standard: Standard
)

data class Default(
    val height: Int,
    val url: String,
    val width: Int
)

data class High(
    val height: Int,
    val url: String,
    val width: Int
)

data class Maxres(
    val height: Int,
    val url: String,
    val width: Int
)

data class Medium(
    val height: Int,
    val url: String,
    val width: Int
)

data class Standard(
    val height: Int,
    val url: String,
    val width: Int
)

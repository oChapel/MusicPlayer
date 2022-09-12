package com.och.musicplayer.data.dto

import com.och.musicplayer.data.dto.YouTubeItem.Companion.ITEM_TOP100
import com.och.musicplayer.data.dto.YouTubeItem.Companion.ITEM_TOP10
import com.och.musicplayer.data.network.YouTubeApi

data class PlaylistSchema(
    val etag: String,
    val items: List<PlaylistItem>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo
)

data class PlaylistItem(
    val etag: String,
    val id: String,
    val kind: String,
    val snippet: Snippet
) : YouTubeItem {

    override fun getViewHolderType(): Int {
        return when (snippet.playlistId) {
            YouTubeApi.TOP10_PLAYLIST -> ITEM_TOP10
            else -> ITEM_TOP100
        }
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

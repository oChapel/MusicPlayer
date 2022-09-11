package com.och.musicplayer.data.dto

data class SearchResultSchema(
    val etag: String,
    val items: List<SearchVideo>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo,
    val regionCode: String
)

data class SearchVideo(
    val etag: String,
    val id: Id,
    val kind: String,
    val snippet: SearchSnippet
) : YoutubeItem {
    override fun getViewHolderType(): Int = YoutubeItem.ITEM_SEARCH_DETAIL
}

data class Id(
    val kind: String,
    val videoId: String
)

data class SearchSnippet(
    val channelId: String,
    val channelTitle: String,
    val description: String,
    val liveBroadcastContent: String,
    val publishTime: String,
    val publishedAt: String,
    val thumbnails: Thumbnails,
    val title: String
)

package com.och.musicplayer.data.network

import com.och.musicplayer.data.dto.PlaylistSchema
import com.och.musicplayer.data.dto.SearchResultSchema
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApi {

    @GET("playlistItems")
    suspend fun getPlaylist(
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("maxResults") count: Int,
        @Query("key") apiKey: String
    ): PlaylistSchema

    @GET("search")
    suspend fun searchForVideo(
        @Query("part") part: String,
        @Query("maxResults") count: Int,
        @Query("q") q: String,
        @Query("type") type: String,
        @Query("key") apiKey: String
    ): SearchResultSchema

    companion object {
        const val PART_SNIPPET = "snippet"
        const val TYPE_VIDEO = "video"
        const val TOP10_PLAYLIST = "RDCLAK5uy_nRedcFlCjA5OFdDQH_XGStGXN8q1mM2qw"
        const val TOP100_PLAYLIST = "PL4fGSI1pDJn7524WZdmWAIRc6cQ3vUzZK"
    }
}

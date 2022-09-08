package com.och.musicplayer.data.network

import com.och.musicplayer.data.dto.PlaylistSchema
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApi {

    @GET("playlistItems")
    suspend fun getPlaylist(
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("maxResults") count: Int
    ): PlaylistSchema

    companion object {
        const val PART_SNIPPET = "snippet"
        const val TOP10_PLAYLIST = "RDTMAK5uy_k5UUl0lmrrfrjMpsT0CoMpdcBz1ruAO1k"
        const val TOP100_PLAYLIST = "PL4fGSI1pDJn7524WZdmWAIRc6cQ3vUzZK"
    }
}

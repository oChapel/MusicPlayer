package com.och.musicplayer.data.dto

import java.io.Serializable

data class YoutubeItems(
    val list: List<YoutubeItem>
) : Serializable

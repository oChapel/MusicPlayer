package com.och.musicplayer.ui.adapter

import com.och.musicplayer.data.dto.YouTubeItem

sealed class ClickEvent {

    class OnItemClicked(val item: YouTubeItem) : ClickEvent()
}
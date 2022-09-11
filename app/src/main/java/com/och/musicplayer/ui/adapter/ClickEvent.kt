package com.och.musicplayer.ui.adapter

import com.och.musicplayer.data.dto.YoutubeItem

sealed class ClickEvent {

    class OnItemClicked(val item: YoutubeItem) : ClickEvent()
}
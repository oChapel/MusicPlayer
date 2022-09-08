package com.och.musicplayer.ui.adapter

import com.och.musicplayer.data.dto.Song

sealed class ClickEvent {

    class OnItemClicked(val song: Song) : ClickEvent()
}
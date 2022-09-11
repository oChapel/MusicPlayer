package com.och.musicplayer.data.dto

interface YoutubeItem {

    fun getViewHolderType(): Int

    companion object {
        const val ITEM_TOP10 = 0
        const val ITEM_TOP100 = 1
        const val ITEM_SEARCH_DETAIL = 2
    }
}

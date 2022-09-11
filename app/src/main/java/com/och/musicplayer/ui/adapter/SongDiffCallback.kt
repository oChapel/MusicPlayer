package com.och.musicplayer.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.och.musicplayer.data.dto.SearchVideo
import com.och.musicplayer.data.dto.Song
import com.och.musicplayer.data.dto.YoutubeItem

object SongDiffCallback : DiffUtil.ItemCallback<YoutubeItem>() {

    override fun areItemsTheSame(oldItem: YoutubeItem, newItem: YoutubeItem): Boolean {
        return if (oldItem is Song && newItem is Song) {
            oldItem.id == newItem.id
        } else if (oldItem is SearchVideo && newItem is SearchVideo) {
            oldItem.id.videoId == newItem.id.videoId
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItem: YoutubeItem, newItem: YoutubeItem): Boolean {
        return if (oldItem is Song && newItem is Song) {
            oldItem == newItem
        } else if (oldItem is SearchVideo && newItem is SearchVideo) {
            oldItem == newItem
        } else {
            false
        }
    }
}

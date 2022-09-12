package com.och.musicplayer.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.och.musicplayer.data.dto.SearchItem
import com.och.musicplayer.data.dto.PlaylistItem
import com.och.musicplayer.data.dto.YouTubeItem

object SongDiffCallback : DiffUtil.ItemCallback<YouTubeItem>() {

    override fun areItemsTheSame(oldItem: YouTubeItem, newItem: YouTubeItem): Boolean {
        return if (oldItem is PlaylistItem && newItem is PlaylistItem) {
            oldItem.id == newItem.id
        } else if (oldItem is SearchItem && newItem is SearchItem) {
            oldItem.id.videoId == newItem.id.videoId
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItem: YouTubeItem, newItem: YouTubeItem): Boolean {
        return if (oldItem is PlaylistItem && newItem is PlaylistItem) {
            oldItem == newItem
        } else if (oldItem is SearchItem && newItem is SearchItem) {
            oldItem == newItem
        } else {
            false
        }
    }
}

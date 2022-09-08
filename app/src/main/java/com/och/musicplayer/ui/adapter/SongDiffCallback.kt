package com.och.musicplayer.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.och.musicplayer.data.dto.Song

object SongDiffCallback : DiffUtil.ItemCallback<Song>() {

    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.snippet == newItem.snippet
    }
}
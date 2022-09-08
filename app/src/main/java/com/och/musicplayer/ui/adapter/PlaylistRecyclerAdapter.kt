package com.och.musicplayer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.och.musicplayer.data.dto.Song
import com.och.musicplayer.data.dto.Song.Companion.TOP10_SONG
import com.och.musicplayer.databinding.ItemTop100Binding
import com.och.musicplayer.databinding.ItemTop10Binding
import com.och.musicplayer.ui.adapter.holders.SongViewHolder
import com.och.musicplayer.ui.adapter.holders.Top100SongHolder
import com.och.musicplayer.ui.adapter.holders.Top10SongHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class PlaylistRecyclerAdapter : ListAdapter<Song, SongViewHolder>(SongDiffCallback) {

    private val clickFlow = MutableSharedFlow<ClickEvent>(extraBufferCapacity = 1)

    fun getClickFlow(): Flow<ClickEvent> = clickFlow

    override fun getItemViewType(position: Int): Int = getItem(position).getViewHolderType()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return when (viewType) {
            TOP10_SONG -> Top10SongHolder(
                ItemTop10Binding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), clickFlow
            )
            else -> Top100SongHolder(
                ItemTop100Binding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), clickFlow
            )
        }
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

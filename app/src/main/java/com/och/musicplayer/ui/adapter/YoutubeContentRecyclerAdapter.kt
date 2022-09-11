package com.och.musicplayer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.och.musicplayer.data.dto.YoutubeItem
import com.och.musicplayer.data.dto.YoutubeItem.Companion.ITEM_TOP10
import com.och.musicplayer.data.dto.YoutubeItem.Companion.ITEM_TOP100
import com.och.musicplayer.databinding.ItemSearchResultBinding
import com.och.musicplayer.databinding.ItemTop100Binding
import com.och.musicplayer.databinding.ItemTop10Binding
import com.och.musicplayer.ui.adapter.holders.SearchResultItemHolder
import com.och.musicplayer.ui.adapter.holders.SongViewHolder
import com.och.musicplayer.ui.adapter.holders.Top100SongHolder
import com.och.musicplayer.ui.adapter.holders.Top10SongHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class YoutubeContentRecyclerAdapter : ListAdapter<YoutubeItem, SongViewHolder<YoutubeItem>>(SongDiffCallback) {

    private val clickFlow = MutableSharedFlow<ClickEvent>(extraBufferCapacity = 1)

    fun getClickFlow(): Flow<ClickEvent> = clickFlow

    override fun getItemViewType(position: Int): Int = getItem(position).getViewHolderType()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder<YoutubeItem> {
        val vh = when (viewType) {
            ITEM_TOP10 -> Top10SongHolder(
                ItemTop10Binding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), clickFlow
            )
            ITEM_TOP100 -> Top100SongHolder(
                ItemTop100Binding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), clickFlow
            )
            else -> SearchResultItemHolder(
                ItemSearchResultBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), clickFlow
            )
        }
        return vh as SongViewHolder<YoutubeItem>
    }

    override fun onBindViewHolder(holder: SongViewHolder<YoutubeItem>, position: Int) {
        holder.bind(getItem(position))
    }
}

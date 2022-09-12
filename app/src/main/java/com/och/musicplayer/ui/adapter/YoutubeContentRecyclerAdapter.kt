package com.och.musicplayer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.och.musicplayer.data.dto.YouTubeItem
import com.och.musicplayer.data.dto.YouTubeItem.Companion.ITEM_TOP10
import com.och.musicplayer.data.dto.YouTubeItem.Companion.ITEM_TOP100
import com.och.musicplayer.databinding.ItemSearchResultBinding
import com.och.musicplayer.databinding.ItemTop100Binding
import com.och.musicplayer.databinding.ItemTop10Binding
import com.och.musicplayer.ui.adapter.holders.SearchResultItemHolder
import com.och.musicplayer.ui.adapter.holders.SongViewHolder
import com.och.musicplayer.ui.adapter.holders.Top100ItemHolder
import com.och.musicplayer.ui.adapter.holders.Top10ItemHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class YoutubeContentRecyclerAdapter : ListAdapter<YouTubeItem, SongViewHolder<YouTubeItem>>(SongDiffCallback) {

    private val clickFlow = MutableSharedFlow<ClickEvent>(extraBufferCapacity = 1)

    fun getClickFlow(): Flow<ClickEvent> = clickFlow

    override fun getItemViewType(position: Int): Int = getItem(position).getViewHolderType()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder<YouTubeItem> {
        val vh = when (viewType) {
            ITEM_TOP10 -> Top10ItemHolder(
                ItemTop10Binding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), clickFlow
            )
            ITEM_TOP100 -> Top100ItemHolder(
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
        return vh as SongViewHolder<YouTubeItem>
    }

    override fun onBindViewHolder(holder: SongViewHolder<YouTubeItem>, position: Int) {
        holder.bind(getItem(position))
    }
}

package com.och.musicplayer.ui.adapter.holders

import android.view.View
import com.och.musicplayer.data.dto.PlaylistItem
import com.och.musicplayer.databinding.ItemTop10Binding
import com.och.musicplayer.ui.adapter.ClickEvent
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.MutableSharedFlow

class Top10ItemHolder(
    private val binding: ItemTop10Binding,
    private val clickFLow: MutableSharedFlow<ClickEvent>
) : SongViewHolder<PlaylistItem>(binding.root), View.OnClickListener {

    private var playlistItem: PlaylistItem? = null

    init {
        binding.root.setOnClickListener(this)
    }

    override fun bind(item: PlaylistItem) {
        this.playlistItem = item
        val imageUrl = item.snippet.thumbnails.maxres?.url ?: item.snippet.thumbnails.high.url
        Picasso.get().load(imageUrl).into(binding.top10SongCover)
        binding.top10SongTitle.text = item.snippet.title
        binding.top10SongArtist.text = item.snippet.videoOwnerChannelTitle
    }

    override fun onClick(view: View?) {
        playlistItem?.let { if (view == binding.root) clickFLow.tryEmit(ClickEvent.OnItemClicked(it)) }
    }
}
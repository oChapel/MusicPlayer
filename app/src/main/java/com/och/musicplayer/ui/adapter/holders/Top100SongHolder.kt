package com.och.musicplayer.ui.adapter.holders

import android.view.View
import com.och.musicplayer.data.dto.Song
import com.och.musicplayer.databinding.ItemTop100Binding
import com.och.musicplayer.ui.adapter.ClickEvent
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.MutableSharedFlow

class Top100SongHolder(
    private val binding: ItemTop100Binding,
    private val clickFLow: MutableSharedFlow<ClickEvent>
) : SongViewHolder<Song>(binding.root), View.OnClickListener {

    private var song: Song? = null

    init {
        binding.root.setOnClickListener(this)
    }

    override fun bind(item: Song) {
        this.song = item
        Picasso.get().load(item.snippet.thumbnails.medium.url).into(binding.top100SongCover)
        binding.top100SongTitle.text = item.snippet.title
        binding.top100SongArtist.text = item.snippet.videoOwnerChannelTitle
    }

    override fun onClick(view: View?) {
        song?.let { if (view == binding.root) clickFLow.tryEmit(ClickEvent.OnItemClicked(it)) }
    }
}

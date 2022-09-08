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
) : SongViewHolder(binding.root), View.OnClickListener {

    private var song: Song? = null

    init{
        binding.root.setOnClickListener(this)
    }

    override fun bind(song: Song) {
        this.song = song
        binding.top100SongCover.layoutParams.apply { //TODO check if works
            width = song.snippet.thumbnails.default.width
            height = song.snippet.thumbnails.default.height
        }
        Picasso.get().load(song.snippet.thumbnails.default.url).into(binding.top100SongCover)
        binding.top100SongTitle.text = song.snippet.title
        binding.top100SongArtist.text = song.snippet.videoOwnerChannelTitle //TODO
    }

    override fun onClick(view: View?) {
        song?.let { if (view == binding.root) clickFLow.tryEmit(ClickEvent.OnItemClicked(it)) }
    }
}

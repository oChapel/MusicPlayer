package com.och.musicplayer.ui.adapter.holders

import android.view.View
import com.och.musicplayer.data.dto.Song
import com.och.musicplayer.databinding.ItemTop10Binding
import com.och.musicplayer.ui.adapter.ClickEvent
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.MutableSharedFlow

class Top10SongHolder(
    private val binding: ItemTop10Binding,
    private val clickFLow: MutableSharedFlow<ClickEvent>
) : SongViewHolder(binding.root), View.OnClickListener {

    private var song: Song? = null

    init{
        binding.root.setOnClickListener(this)
    }

    override fun bind(song: Song) {
        this.song = song
        binding.top10SongCover.layoutParams.apply { //TODO check if works
            width = song.snippet.thumbnails.medium.width
            height = song.snippet.thumbnails.medium.height
        }
        Picasso.get().load(song.snippet.thumbnails.medium.url).into(binding.top10SongCover)
        binding.top10SongTitle.text = song.snippet.title
        binding.top10SongArtist.text = song.snippet.videoOwnerChannelTitle //TODO
    }

    override fun onClick(view: View?) {
        song?.let { if (view == binding.root) clickFLow.tryEmit(ClickEvent.OnItemClicked(it)) }
    }
}
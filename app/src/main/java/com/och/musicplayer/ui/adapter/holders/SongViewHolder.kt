package com.och.musicplayer.ui.adapter.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.och.musicplayer.data.dto.YoutubeItem

abstract class SongViewHolder<T : YoutubeItem>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: T)
}

package com.och.musicplayer.ui.adapter.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.och.musicplayer.data.dto.YouTubeItem

abstract class SongViewHolder<T : YouTubeItem>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: T)
}

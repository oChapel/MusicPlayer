package com.och.musicplayer.ui.listeners

import android.view.View
import android.widget.SeekBar
import com.sothree.slidinguppanel.SlidingUpPanelLayout

interface SlidingUpPanelListeners : SlidingUpPanelLayout.PanelSlideListener, SeekBar.OnSeekBarChangeListener {

    override fun onPanelSlide(panel: View?, slideOffset: Float) {}

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {}

    override fun onStartTrackingTouch(p0: SeekBar?) {}
}
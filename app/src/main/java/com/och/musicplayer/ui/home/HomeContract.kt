package com.och.musicplayer.ui.home

import com.och.musicplayer.data.dto.PlaylistItem
import com.och.musicplayer.data.dto.YouTubeItem
import com.och.musicplayer.ui.home.states.HomeScreenEffect
import com.och.musicplayer.ui.home.states.HomeScreenState
import com.och.mvi.fragments.FragmentContract

class HomeContract {

    interface ViewModel : FragmentContract.ViewModel<HomeScreenState, HomeScreenEffect> {
        fun reload()
    }

    interface View : FragmentContract.View {
        fun setProgressVisibility(isVisible: Boolean)
        fun showPlaylistsContents(top10List: List<PlaylistItem>, top100List: List<PlaylistItem>)
        fun showErrorDialog(error: Throwable)
    }

    interface Host : FragmentContract.Host {
        fun showErrorDialog(error: Throwable)
        fun loadVideos(list: List<YouTubeItem>, startIndex: Int)
        fun isPlayerInFocus(): Boolean
    }
}

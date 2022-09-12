package com.och.musicplayer.ui.home.states

import com.och.musicplayer.data.dto.PlaylistItem
import com.och.musicplayer.ui.home.HomeContract
import com.och.mvi.states.AbstractState

open class HomeScreenState(
    val isProgressVisible: Boolean = false,
    val playlists: Pair<List<PlaylistItem>, List<PlaylistItem>> = Pair(listOf(), listOf())
) : AbstractState<HomeContract.View, HomeScreenState>() {

    override fun visit(screen: HomeContract.View) {
        screen.setProgressVisibility(isProgressVisible)
        screen.showPlaylistsContents(playlists.first, playlists.second)
    }

    class Loading : HomeScreenState(true) {
        override fun merge(prevState: HomeScreenState): HomeScreenState {
            return HomeScreenState(isProgressVisible, prevState.playlists)
        }
    }

    class LoadPlaylists(playlists: Pair<List<PlaylistItem>, List<PlaylistItem>>) : HomeScreenState(false, playlists) {
        override fun merge(prevState: HomeScreenState): HomeScreenState {
            return HomeScreenState(isProgressVisible, playlists)
        }
    }

    class Error : HomeScreenState(false) {
        override fun merge(prevState: HomeScreenState): HomeScreenState {
            return HomeScreenState(isProgressVisible, prevState.playlists)
        }
    }
}

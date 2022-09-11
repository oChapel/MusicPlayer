package com.och.musicplayer.ui.search

import com.och.musicplayer.data.dto.YoutubeItem
import com.och.musicplayer.ui.search.state.SearchScreenEffect
import com.och.musicplayer.ui.search.state.SearchScreenState
import com.och.mvi.fragments.FragmentContract

class SearchContract {

    interface ViewModel : FragmentContract.ViewModel<SearchScreenState, SearchScreenEffect> {
        fun searchForVideo(query: String)
    }

    interface View : FragmentContract.View {
        fun setProgressVisibility(isVisible: Boolean)
        fun showSearchResults(list: List<YoutubeItem>)
        fun showErrorDialog(error: Throwable)
    }

    interface Host : FragmentContract.Host {
        fun showErrorDialog(error: Throwable)
    }
}

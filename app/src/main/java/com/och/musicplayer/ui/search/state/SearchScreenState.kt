package com.och.musicplayer.ui.search.state

import com.och.musicplayer.data.dto.SearchItem
import com.och.musicplayer.ui.search.SearchContract
import com.och.mvi.states.AbstractState

open class SearchScreenState(
    val isProgressVisible: Boolean = false,
    val list: List<SearchItem> = listOf()
) : AbstractState<SearchContract.View, SearchScreenState>() {

    override fun visit(screen: SearchContract.View) {
        screen.setProgressVisibility(isProgressVisible)
        screen.showSearchResults(list)
    }

    class Loading : SearchScreenState(true) {
        override fun merge(prevState: SearchScreenState): SearchScreenState {
            return SearchScreenState(isProgressVisible, prevState.list)
        }
    }

    class ShowResults(list: List<SearchItem>) : SearchScreenState(false, list) {
        override fun merge(prevState: SearchScreenState): SearchScreenState {
            return SearchScreenState(isProgressVisible, list)
        }
    }

    class Error : SearchScreenState(false) {
        override fun merge(prevState: SearchScreenState): SearchScreenState {
            return SearchScreenState(isProgressVisible, prevState.list)
        }
    }
}
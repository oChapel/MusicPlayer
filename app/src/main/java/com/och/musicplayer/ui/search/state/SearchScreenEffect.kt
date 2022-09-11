package com.och.musicplayer.ui.search.state

import com.och.musicplayer.ui.search.SearchContract
import com.och.mvi.states.AbstractEffect

sealed class SearchScreenEffect : AbstractEffect<SearchContract.View>() {

    class ShowError(private val error: Throwable) : SearchScreenEffect() {
        override fun handle(screen: SearchContract.View) {
            screen.showErrorDialog(error)
        }
    }
}
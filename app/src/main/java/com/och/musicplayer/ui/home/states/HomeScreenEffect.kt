package com.och.musicplayer.ui.home.states

import com.och.musicplayer.ui.home.HomeContract
import com.och.mvi.states.AbstractEffect

sealed class HomeScreenEffect : AbstractEffect<HomeContract.View>() {

    class ShowError(private val error: Throwable) : HomeScreenEffect() {
        override fun handle(screen: HomeContract.View) {
            screen.showErrorDialog(error)
        }
    }
}
package com.och.musicplayer.ui.home.states

import com.och.musicplayer.ui.home.HomeContract
import com.och.mvi.states.AbstractEffect

sealed class HomeScreenEffect : AbstractEffect<HomeContract.View>() {
}
package com.och.musicplayer.ui.home.states

import com.och.musicplayer.ui.home.HomeContract
import com.och.mvi.states.AbstractState

sealed class HomeScreenState : AbstractState<HomeContract.View, HomeScreenState>() {
}
package com.och.musicplayer.ui.home

import com.och.musicplayer.data.MusicPlayerRepository
import com.och.musicplayer.ui.home.states.HomeScreenEffect
import com.och.musicplayer.ui.home.states.HomeScreenState
import com.och.mvi.MviViewModel

class HomeViewModel(private val repository: MusicPlayerRepository) : MviViewModel<
        HomeContract.View,
        HomeScreenState,
        HomeScreenEffect>(), HomeContract.ViewModel {
}
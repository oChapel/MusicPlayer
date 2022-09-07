package com.och.musicplayer.ui.home

import com.och.musicplayer.ui.home.states.HomeScreenEffect
import com.och.musicplayer.ui.home.states.HomeScreenState
import com.och.mvi.fragments.FragmentContract

class HomeContract {

    interface ViewModel : FragmentContract.ViewModel<HomeScreenState, HomeScreenEffect> {

    }

    interface View : FragmentContract.View {

    }

    interface Host : FragmentContract.Host {

    }
}
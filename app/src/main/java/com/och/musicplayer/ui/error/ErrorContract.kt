package com.och.musicplayer.ui.error

import com.och.musicplayer.ui.error.state.ErrorScreenEffect
import com.och.musicplayer.ui.error.state.ErrorScreenState
import com.och.mvi.fragments.FragmentContract

interface ErrorContract {
    interface ViewModel : FragmentContract.ViewModel<ErrorScreenState, ErrorScreenEffect>
    interface View : FragmentContract.View
    interface Host : FragmentContract.Host
}
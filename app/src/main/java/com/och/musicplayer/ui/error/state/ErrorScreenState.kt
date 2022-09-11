package com.och.musicplayer.ui.error.state

import com.och.mvi.states.AbstractState
import com.och.musicplayer.ui.error.ErrorContract

sealed class ErrorScreenState : AbstractState<ErrorContract.View, ErrorScreenState>()

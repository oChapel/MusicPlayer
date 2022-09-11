package com.och.musicplayer.ui.error.state

import com.och.musicplayer.ui.error.ErrorContract
import com.och.mvi.states.AbstractEffect

sealed class ErrorScreenEffect : AbstractEffect<ErrorContract.View>()

package com.och.musicplayer.util.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersHolder {

    fun getMain(): CoroutineDispatcher

    fun getIO(): CoroutineDispatcher
}
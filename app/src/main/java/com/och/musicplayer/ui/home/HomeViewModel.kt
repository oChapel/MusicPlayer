package com.och.musicplayer.ui.home

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.och.musicplayer.data.MusicPlayerRepository
import com.och.musicplayer.ui.home.states.HomeScreenEffect
import com.och.musicplayer.ui.home.states.HomeScreenState
import com.och.musicplayer.util.dispatchers.DispatchersHolder
import com.och.mvi.MviViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MusicPlayerRepository,
    private val dispatchers: DispatchersHolder
) : MviViewModel<
        HomeContract.View,
        HomeScreenState,
        HomeScreenEffect>(), HomeContract.ViewModel {

    private val songsFlow = MutableStateFlow(0)
    private var launch: Job? = null

    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)
        if (event == Lifecycle.Event.ON_CREATE && launch == null) {
            launchJob()
        }
    }

    private fun launchJob() {
        launch = viewModelScope.launch {
            songsFlow
                .onEach { setState(HomeScreenState.Loading()) }
                .flowOn(dispatchers.getMain())
                .map {
                    return@map Pair(
                        repository.getTop10Playlist().items,
                        repository.getTop100Playlist().items
                    )
                }
                .flowOn(dispatchers.getIO())
                .catch { error ->
                    setState(HomeScreenState.Error())
                    setEffect(HomeScreenEffect.ShowError(error))
                }
                .collect { pair -> setState(HomeScreenState.LoadPlaylists(pair)) }
        }
    }

    override fun reload() {
        checkIfJobCompleted()
        if (songsFlow.value == 0) {
            songsFlow.tryEmit(1)
        } else {
            songsFlow.tryEmit(0)
        }
    }

    private fun checkIfJobCompleted() {
        if (launch?.isCompleted == true) {
            launchJob()
        }
    }

    override fun onCleared() {
        launch = null
        super.onCleared()
    }
}

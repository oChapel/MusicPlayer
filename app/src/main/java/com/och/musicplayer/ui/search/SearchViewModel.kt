package com.och.musicplayer.ui.search

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.och.musicplayer.data.MusicPlayerRepository
import com.och.musicplayer.ui.search.state.SearchScreenEffect
import com.och.musicplayer.ui.search.state.SearchScreenState
import com.och.musicplayer.util.dispatchers.DispatchersHolder
import com.och.mvi.MviViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    initQuery: String,
    private val repository: MusicPlayerRepository,
    private val dispatchers: DispatchersHolder
) : MviViewModel<
        SearchContract.View,
        SearchScreenState,
        SearchScreenEffect>(), SearchContract.ViewModel {

    private val searchFlow = MutableStateFlow(initQuery)
    private var launch: Job? = null

    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)
        if (event == Lifecycle.Event.ON_CREATE && launch == null) {
            launchJob()
        }
    }

    private fun launchJob() {
        launch = viewModelScope.launch {
            searchFlow
                .onEach { setState(SearchScreenState.Loading()) }
                .flowOn(dispatchers.getMain())
                .map { query -> repository.searchForVideo(query).items }
                .flowOn(dispatchers.getIO())
                .catch { error ->
                    setState(SearchScreenState.Error())
                    setEffect(SearchScreenEffect.ShowError(error))
                }
                .collect { list -> setState(SearchScreenState.ShowResults(list)) }
        }
    }

    override fun searchForVideo(query: String) {
        checkIfJobCompleted()
        searchFlow.tryEmit(query)
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

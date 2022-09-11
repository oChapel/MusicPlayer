package com.och.musicplayer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.och.musicplayer.App
import com.och.musicplayer.data.MusicPlayerRepository
import com.och.musicplayer.ui.home.HomeViewModel
import com.och.musicplayer.ui.search.SearchViewModel
import com.och.musicplayer.util.dispatchers.DispatchersHolder
import javax.inject.Inject

class MusicPlayerViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    private val appComponent = App.instance.component

    var initQuery: String = ""

    @Inject
    lateinit var repository: MusicPlayerRepository

    @Inject
    lateinit var dispatchers: DispatchersHolder

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        appComponent.inject(this)
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository, dispatchers) as T
        } else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(initQuery, repository, dispatchers) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.och.musicplayer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.och.musicplayer.App
import com.och.musicplayer.data.MusicPlayerRepository
import com.och.musicplayer.ui.home.HomeViewModel
import javax.inject.Inject

class MusicPlayerViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    private val appComponent = App.instance.component

    @Inject
    lateinit var repository: MusicPlayerRepository

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        appComponent.inject(this)
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
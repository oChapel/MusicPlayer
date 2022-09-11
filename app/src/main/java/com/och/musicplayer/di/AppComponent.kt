package com.och.musicplayer.di

import com.och.musicplayer.ui.MusicPlayerViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, DataModule::class, NetworkModule::class])
@Singleton
interface AppComponent {
    fun inject(factory: MusicPlayerViewModelFactory)
}
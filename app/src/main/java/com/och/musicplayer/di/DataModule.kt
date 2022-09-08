package com.och.musicplayer.di

import com.och.musicplayer.data.DefaultMusicPlayerRepository
import com.och.musicplayer.data.MusicPlayerRepository
import com.och.musicplayer.data.network.MusicPlayerNetwork
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideMusicPlayerRepository(remoteDataSource: MusicPlayerNetwork): MusicPlayerRepository {
        return DefaultMusicPlayerRepository(remoteDataSource)
    }
}
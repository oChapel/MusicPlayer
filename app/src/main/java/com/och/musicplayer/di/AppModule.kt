package com.och.musicplayer.di

import com.och.musicplayer.util.dispatchers.DispatchersHolder
import com.och.musicplayer.util.dispatchers.DispatchersHolderImpl
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideDispatchersHolder(): DispatchersHolder {
        return DispatchersHolderImpl()
    }
}
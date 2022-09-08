package com.och.musicplayer

import android.app.Application
import com.och.musicplayer.di.AppComponent

class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
    }
}
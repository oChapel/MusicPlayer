package com.och.musicplayer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.och.musicplayer.R
import com.och.musicplayer.databinding.ActivityMainBinding
import com.och.musicplayer.ui.home.HomeContract

class MainActivity : AppCompatActivity(), HomeContract.Host {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
    }
}

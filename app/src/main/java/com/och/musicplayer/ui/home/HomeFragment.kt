package com.och.musicplayer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.och.musicplayer.databinding.FragmentHomeBinding
import com.och.musicplayer.ui.MusicPlayerViewModelFactory
import com.och.musicplayer.ui.home.states.HomeScreenEffect
import com.och.musicplayer.ui.home.states.HomeScreenState
import com.och.mvi.fragments.HostedFragment

class HomeFragment : HostedFragment<
        HomeContract.View,
        HomeScreenState,
        HomeScreenEffect,
        HomeContract.ViewModel,
        HomeContract.Host>(), HomeContract.View {

    private var binding: FragmentHomeBinding? = null

    override fun createModel(): HomeContract.ViewModel {
        return ViewModelProvider(this, MusicPlayerViewModelFactory())[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}

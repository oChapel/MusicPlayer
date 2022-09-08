package com.och.musicplayer.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.och.musicplayer.R
import com.och.musicplayer.databinding.FragmentHomeBinding
import com.och.musicplayer.ui.MusicPlayerViewModelFactory
import com.och.musicplayer.ui.adapter.PlaylistRecyclerAdapter
import com.och.musicplayer.ui.home.states.HomeScreenEffect
import com.och.musicplayer.ui.home.states.HomeScreenState
import com.och.mvi.fragments.HostedFragment
import kotlinx.coroutines.launch

class HomeFragment : HostedFragment<
        HomeContract.View,
        HomeScreenState,
        HomeScreenEffect,
        HomeContract.ViewModel,
        HomeContract.Host>(), HomeContract.View, View.OnClickListener {

    private var binding: FragmentHomeBinding? = null
    private val rvAdapter = PlaylistRecyclerAdapter()

    override fun createModel(): HomeContract.ViewModel {
        return ViewModelProvider(this, MusicPlayerViewModelFactory())[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            rvAdapter.getClickFlow().collect {
                //TODO navigate to player
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.homeTop10RecyclerView?.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding?.homeTop100RecyclerView?.apply {
            adapter = rvAdapter
            layoutManager = GridLayoutManager(context, 3)
        }

        binding?.homeMiniPlayerSong?.setOnClickListener(this)
        binding?.homeMiniPlayerButtonPrev?.setOnClickListener(this)
        binding?.homeMiniPlayerButtonPlayPause?.setOnClickListener(this)
        binding?.homeMiniPlayerButtonNext?.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //TODO
        val searchView = item.actionView as SearchView
        when (item.itemId) {
            R.id.action_search -> TODO()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding?.homeMiniPlayerSong -> TODO()
            binding?.homeMiniPlayerButtonPrev -> TODO()
            binding?.homeMiniPlayerButtonPlayPause -> TODO()
            binding?.homeMiniPlayerButtonNext -> TODO()
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}

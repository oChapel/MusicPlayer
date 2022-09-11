package com.och.musicplayer.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.och.musicplayer.R
import com.och.musicplayer.data.dto.Song
import com.och.musicplayer.data.dto.YoutubeItems
import com.och.musicplayer.databinding.FragmentHomeBinding
import com.och.musicplayer.ui.MusicPlayerViewModelFactory
import com.och.musicplayer.ui.adapter.ClickEvent
import com.och.musicplayer.ui.adapter.YoutubeContentRecyclerAdapter
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
    private val top10RvAdapter = YoutubeContentRecyclerAdapter()
    private val top100RvAdapter = YoutubeContentRecyclerAdapter()
    private val top10List = ArrayList<Song>() //TODO
    private val top100List = ArrayList<Song>() //TODO
    private val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSearchResultFragment(
                        it
                    )
                )
            }
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean = false
    }

    override fun createModel(): HomeContract.ViewModel {
        return ViewModelProvider(this, MusicPlayerViewModelFactory())[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        lifecycleScope.launch {
            top10RvAdapter.getClickFlow().collect {
                val clickEventItem = (it as ClickEvent.OnItemClicked).item as Song
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToPlayerFragment(
                        YoutubeItems(top10List),
                        top10RvAdapter.currentList.indexOf(clickEventItem)
                    )
                )
            }

            top100RvAdapter.getClickFlow().collect { //TODO
                val clickEventItem = (it as ClickEvent.OnItemClicked).item as Song
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToPlayerFragment(
                        YoutubeItems(top100List),
                        top100RvAdapter.currentList.indexOf(clickEventItem)
                    )
                )
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
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(queryTextListener)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.homeTop10RecyclerView?.apply {
            adapter = top10RvAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding?.homeTop100RecyclerView?.apply {
            adapter = top100RvAdapter
            layoutManager = GridLayoutManager(context, 3)
        }

        binding?.homeMiniPlayerSong?.setOnClickListener(this)
        binding?.homeMiniPlayerButtonPrev?.setOnClickListener(this)
        binding?.homeMiniPlayerButtonPlayPause?.setOnClickListener(this)
        binding?.homeMiniPlayerButtonNext?.setOnClickListener(this)
    }

    override fun setProgressVisibility(isVisible: Boolean) {
        val targetAlpha = if (isVisible) 1F else 0F
        val progressBar = binding?.homeProgress
        if (progressBar?.alpha != targetAlpha) {
            progressBar?.animate()?.alpha(targetAlpha)
                ?.withStartAction(
                    if (isVisible) Runnable { progressBar.visibility = View.VISIBLE } else null
                )
                ?.setDuration(resources.getInteger(android.R.integer.config_shortAnimTime).toLong())
                ?.withEndAction(
                    if (isVisible) null else Runnable { progressBar.visibility = View.INVISIBLE }
                )
        }
    }

    override fun showPlaylistsContents(top10List: List<Song>, top100List: List<Song>) {
        top10RvAdapter.submitList(top10List)
        top100RvAdapter.submitList(top100List)
        this.top10List.addAll(top10List)
        this.top100List.addAll(top100List)
    }

    override fun showErrorDialog(error: Throwable) {
        fragmentHost?.showErrorDialog(error)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding?.homeMiniPlayerSong -> TODO() //TODO navigate to player
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

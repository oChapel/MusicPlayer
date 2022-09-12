package com.och.musicplayer.ui.home

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.och.musicplayer.R
import com.och.musicplayer.data.dto.PlaylistItem
import com.och.musicplayer.databinding.FragmentHomeBinding
import com.och.musicplayer.ui.MusicPlayerViewModelFactory
import com.och.musicplayer.ui.adapter.ClickEvent
import com.och.musicplayer.ui.adapter.YouTubeContentRecyclerAdapter
import com.och.musicplayer.ui.home.states.HomeScreenEffect
import com.och.musicplayer.ui.home.states.HomeScreenState
import com.och.mvi.fragments.HostedFragment
import kotlinx.coroutines.launch

class HomeFragment : HostedFragment<
        HomeContract.View,
        HomeScreenState,
        HomeScreenEffect,
        HomeContract.ViewModel,
        HomeContract.Host>(), HomeContract.View {

    private var binding: FragmentHomeBinding? = null
    private val top10RvAdapter = YouTubeContentRecyclerAdapter()
    private val top100RvAdapter = YouTubeContentRecyclerAdapter()
    private val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSearchFragment(
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
                val clickEventItem = (it as ClickEvent.OnItemClicked).item as PlaylistItem
                fragmentHost?.loadVideos(
                    top10RvAdapter.currentList,
                    top10RvAdapter.currentList.indexOf(clickEventItem)
                )
            }
        }

        lifecycleScope.launch {
            top100RvAdapter.getClickFlow().collect {
                val clickEventItem = (it as ClickEvent.OnItemClicked).item as PlaylistItem
                fragmentHost?.loadVideos(
                    top100RvAdapter.currentList,
                    top100RvAdapter.currentList.indexOf(clickEventItem)
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

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (fragmentHost?.isPlayerInFocus() == false) {
                        requireActivity().moveTaskToBack(true)
                    }
                }
            }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_refresh) {
            model?.reload()
        }
        return super.onOptionsItemSelected(item)
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

    override fun showPlaylistsContents(
        top10List: List<PlaylistItem>,
        top100List: List<PlaylistItem>
    ) {
        top10RvAdapter.submitList(top10List)
        top100RvAdapter.submitList(top100List)
    }

    override fun showErrorDialog(error: Throwable) {
        fragmentHost?.showErrorDialog(error)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}

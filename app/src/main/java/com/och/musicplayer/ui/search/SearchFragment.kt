package com.och.musicplayer.ui.search

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.och.musicplayer.R
import com.och.musicplayer.data.dto.SearchItem
import com.och.musicplayer.data.dto.YouTubeItem
import com.och.musicplayer.databinding.FragmentSearchBinding
import com.och.musicplayer.ui.MusicPlayerViewModelFactory
import com.och.musicplayer.ui.adapter.ClickEvent
import com.och.musicplayer.ui.adapter.YoutubeContentRecyclerAdapter
import com.och.musicplayer.ui.search.state.SearchScreenEffect
import com.och.musicplayer.ui.search.state.SearchScreenState
import com.och.mvi.fragments.HostedFragment
import kotlinx.coroutines.launch

class SearchFragment : HostedFragment<
        SearchContract.View,
        SearchScreenState,
        SearchScreenEffect,
        SearchContract.ViewModel,
        SearchContract.Host>(), SearchContract.View {

    private var binding: FragmentSearchBinding? = null
    private val args: SearchFragmentArgs by navArgs()
    private val rvAdapter = YoutubeContentRecyclerAdapter()
    private val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let { model?.searchForVideo(it) }
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean = false
    }

    override fun createModel(): SearchContract.ViewModel {
        return ViewModelProvider(
            this,
            MusicPlayerViewModelFactory().apply { this.initQuery = args.query }
        )[SearchViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        lifecycleScope.launch {
            rvAdapter.getClickFlow().collect {
                val clickEventItem = (it as ClickEvent.OnItemClicked).item as SearchItem
                fragmentHost?.loadVideos(
                    rvAdapter.currentList,
                    rvAdapter.currentList.indexOf(clickEventItem)
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(queryTextListener)
        menu.findItem(R.id.action_refresh).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.searchResultRecyclerView?.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context)
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (fragmentHost?.isPlayerInFocus() == false) {
                        findNavController().popBackStack()
                    }
                }
            }
        )
    }

    override fun setProgressVisibility(isVisible: Boolean) {
        val targetAlpha = if (isVisible) 1F else 0F
        val progressBar = binding?.searchResultProgress
        val rv = binding?.searchResultRecyclerView
        if (progressBar?.alpha != targetAlpha) {
            progressBar?.animate()?.alpha(targetAlpha)
                ?.withStartAction(
                    if (isVisible) Runnable { progressBar.visibility = View.VISIBLE } else null
                )
                ?.setDuration(resources.getInteger(android.R.integer.config_shortAnimTime).toLong())
                ?.withEndAction(
                    if (isVisible) null else Runnable { progressBar.visibility = View.INVISIBLE }
                )

            rv?.animate()?.alpha(1 - targetAlpha)
                ?.withStartAction(
                    if (isVisible) null else Runnable { rv.visibility = View.VISIBLE }
                )
                ?.setDuration(resources.getInteger(android.R.integer.config_shortAnimTime).toLong())
                ?.withEndAction(
                    if (isVisible) Runnable { rv.visibility = View.INVISIBLE } else null
                )
        }
    }

    override fun showSearchResults(list: List<YouTubeItem>) {
        rvAdapter.submitList(list)
    }

    override fun showErrorDialog(error: Throwable) {
        fragmentHost?.showErrorDialog(error)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}

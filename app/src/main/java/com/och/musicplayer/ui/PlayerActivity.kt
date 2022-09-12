package com.och.musicplayer.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.och.musicplayer.App
import com.och.musicplayer.BuildConfig
import com.och.musicplayer.R
import com.och.musicplayer.data.dto.SearchItem
import com.och.musicplayer.data.dto.PlaylistItem
import com.och.musicplayer.data.dto.YouTubeItem
import com.och.musicplayer.databinding.ActivityPlayerBinding
import com.och.musicplayer.di.DaggerAppComponent
import com.och.musicplayer.ui.error.ErrorFragment
import com.och.musicplayer.ui.home.HomeContract
import com.och.musicplayer.ui.listeners.SlidingUpPanelListeners
import com.och.musicplayer.ui.listeners.YouTubePlayerListeners
import com.och.musicplayer.ui.search.SearchContract
import com.och.musicplayer.util.Utils
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.squareup.picasso.Picasso

class PlayerActivity : AppCompatActivity(), View.OnClickListener, HomeContract.Host,
    SearchContract.Host, YouTubePlayerListeners, SlidingUpPanelListeners {

    private lateinit var binding: ActivityPlayerBinding

    private val currentQueue = ArrayList<YouTubeItem>()
    private var currentPositionInQueue: Int = 0
    private var youtubePlayer: YouTubePlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.component = DaggerAppComponent.create()

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val youTubePlayerFragment =
            binding.playerYoutubeView.getFragment<YouTubePlayerSupportFragmentX>()
        youTubePlayerFragment.initialize(BuildConfig.YOUTUBE_API_KEY, this)

        binding.playerButtonPlayPause.setOnClickListener(this)
        binding.playerButtonPrev.setOnClickListener(this)
        binding.playerButtonNext.setOnClickListener(this)
        binding.miniPlayerButtonPlayPause.setOnClickListener(this)
        binding.miniPlayerButtonPrev.setOnClickListener(this)
        binding.miniPlayerButtonNext.setOnClickListener(this)
        binding.playerHide.setOnClickListener(this)

        binding.playerSeekbar.setOnSeekBarChangeListener(this)
        binding.miniPlayerSeekbar.setOnSeekBarChangeListener(this)

        binding.root.addPanelSlideListener(this)
        binding.miniPlayerTitle.isSelected = true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.player_toolbar_menu, menu)
        return true
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?, wasRestored: Boolean
    ) {
        youtubePlayer = player
        youtubePlayer?.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS)
        youtubePlayer?.setPlaylistEventListener(this)
        youtubePlayer?.setPlayerStateChangeListener(this)
        youtubePlayer?.setPlaybackEventListener(this)

        binding.playerCurrentTime.text = youtubePlayer?.currentTimeMillis?.toString()
        binding.playerDurationTime.text = youtubePlayer?.durationMillis?.toString()
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        result: YouTubeInitializationResult?
    ) {
        result?.let { error ->
            if (error.isUserRecoverableError) {
                error.getErrorDialog(this, YouTubePlayerListeners.REQUEST_DIALOG_INT).show()
            } else {
                ErrorFragment.newInstance(
                    R.string.error_dialog_title, R.string.player_error, android.R.string.ok
                ).apply {
                    activity?.supportFragmentManager?.let { show(it, ErrorFragment.TAG) }
                }
            }
        }
    }

    override fun onPrevious() {
        currentPositionInQueue--
        updateTextViews(currentQueue[currentPositionInQueue])
    }

    override fun onNext() {
        currentPositionInQueue++
        updateTextViews(currentQueue[currentPositionInQueue])
    }

    override fun onLoaded(videoId: String?) {
        binding.playerCurrentTime.text =
            Utils.getStringTime(youtubePlayer?.currentTimeMillis) //TODO
        binding.playerDurationTime.text = Utils.getStringTime(youtubePlayer?.durationMillis)

        binding.playerSeekbar.progress = youtubePlayer?.currentTimeMillis!! //TODO
        binding.playerSeekbar.max = youtubePlayer?.durationMillis!!

        val url = currentQueue[currentPositionInQueue].let {
            if (it is PlaylistItem) it.snippet.thumbnails.maxres?.url
                ?: it.snippet.thumbnails.high.url else (it as SearchItem).snippet.thumbnails.high.url
        }
        Picasso.get().load(url).into(binding.miniPlayerImageCover)

        binding.miniPlayer.post { binding.root.panelHeight = binding.miniPlayer.height }
    }

    override fun onPlaying() {
        binding.playerButtonPlayPause.setImageResource(R.drawable.ic_pause)
        binding.miniPlayerButtonPlayPause.setImageResource(R.drawable.ic_pause)
    }

    override fun onPaused() {
        binding.playerButtonPlayPause.setImageResource(R.drawable.ic_play)
        binding.miniPlayerButtonPlayPause.setImageResource(R.drawable.ic_play)
    }

    override fun onStopped() {
        binding.playerButtonPlayPause.setImageResource(R.drawable.ic_play)
        binding.miniPlayerButtonPlayPause.setImageResource(R.drawable.ic_play)
    }

    override fun onSeekTo(i: Int) {
        binding.playerCurrentTime.text =
            Utils.getStringTime(youtubePlayer?.currentTimeMillis)
    }

    override fun onError(reason: YouTubePlayer.ErrorReason?) {
        //TODO ErrorReason.UNAUTHORIZED_OVERLAY
    }

    override fun onPanelStateChanged(
        panel: View?,
        previousState: SlidingUpPanelLayout.PanelState?,
        newState: SlidingUpPanelLayout.PanelState?
    ) {
        if (previousState == SlidingUpPanelLayout.PanelState.COLLAPSED &&
            newState == SlidingUpPanelLayout.PanelState.DRAGGING
        ) {
            binding.miniPlayer.visibility = View.GONE
        } else if (previousState == SlidingUpPanelLayout.PanelState.DRAGGING &&
            newState == SlidingUpPanelLayout.PanelState.COLLAPSED
        ) {
            binding.miniPlayer.visibility = View.VISIBLE
        }
    }

    override fun onStopTrackingTouch(seekbar: SeekBar?) {
        youtubePlayer?.seekToMillis(seekbar?.progress!!)
    }

    override fun onClick(view: View?) {
        if (view == binding.playerButtonPlayPause || view == binding.miniPlayerButtonPlayPause) {
            if (youtubePlayer?.isPlaying == true) {
                youtubePlayer?.pause()
            } else {
                youtubePlayer?.play()
            }
        } else if (view == binding.playerButtonNext || view == binding.miniPlayerButtonNext) {
            if (youtubePlayer?.hasNext() == true) {
                youtubePlayer?.next()
            } else {
                showToast(R.string.player_end_of_queue)
            }
        } else if (view == binding.playerButtonPrev || view == binding.miniPlayerButtonPrev) {
            if (youtubePlayer?.hasPrevious() == true) {
                youtubePlayer?.previous()
            } else {
                showToast(R.string.player_start_of_queue)
            }
        } else if (view == binding.playerHide) {
            binding.root.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
    }

    override fun loadVideos(list: List<YouTubeItem>, startIndex: Int) {
        currentQueue.apply {
            clear()
            addAll(list)
        }
        currentPositionInQueue = startIndex
        youtubePlayer?.loadVideos(getVideoIdList(list), startIndex, 0)
        binding.root.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        updateTextViews(currentQueue[currentPositionInQueue])
    }

    override fun isPlayerInFocus(): Boolean {
        val isInFocus = binding.root.panelState == SlidingUpPanelLayout.PanelState.EXPANDED
        if (isInFocus) {
            binding.root.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
        return isInFocus
    }

    override fun showErrorDialog(error: Throwable) {
        error.printStackTrace()
        ErrorFragment.newInstance(
            R.string.error_dialog_title, R.string.error_dialog_message, android.R.string.ok
        ).apply {
            setError(error)
            show(supportFragmentManager, ErrorFragment.TAG)
        }
    }

    private fun updateTextViews(item: YouTubeItem) {
        if (item is PlaylistItem) {
            binding.playerTitle.text = item.snippet.title
            binding.playerChannel.text = item.snippet.videoOwnerChannelTitle
            binding.miniPlayerTitle.text = item.snippet.title
            binding.miniPlayerChannel.text = item.snippet.videoOwnerChannelTitle
        } else if (item is SearchItem) {
            binding.playerTitle.text = item.snippet.title
            binding.playerChannel.text = item.snippet.channelTitle
            binding.miniPlayerTitle.text = item.snippet.title
            binding.miniPlayerChannel.text = item.snippet.channelTitle
        }
    }

    private fun getVideoIdList(content: List<YouTubeItem>): List<String> {
        val list = ArrayList<String>()
        for (item in content) {
            when (item) {
                is PlaylistItem -> list.add(item.snippet.resourceId.videoId)
                is SearchItem -> list.add(item.id.videoId)
            }
        }
        return list
    }

    private fun showToast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }
}

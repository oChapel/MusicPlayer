package com.och.musicplayer.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.och.musicplayer.BuildConfig
import com.och.musicplayer.R
import com.och.musicplayer.data.dto.SearchVideo
import com.och.musicplayer.data.dto.Song
import com.och.musicplayer.data.dto.YoutubeItem
import com.och.musicplayer.databinding.FragmentPlayerBinding
import com.och.musicplayer.ui.error.ErrorFragment
import com.och.musicplayer.util.Utils

class PlayerFragment : Fragment(), View.OnClickListener,
    YouTubePlayerListeners {

    private var binding: FragmentPlayerBinding? = null
    private var youtubePlayer: YouTubePlayer? = null
    private var currentPositionInPlaylist: Int = 0
    private val args: PlayerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentPositionInPlaylist = args.startIndex

        val youTubePlayerFragment =
            binding?.playerYoutubeView?.getFragment<YouTubePlayerSupportFragmentX?>()
        youTubePlayerFragment?.initialize(BuildConfig.YOUTUBE_API_KEY, this)

        updateTextViews(args.youtubeContent.list[currentPositionInPlaylist])

        binding?.playerButtonPlayPause?.setOnClickListener(this)
        binding?.playerButtonPrev?.setOnClickListener(this)
        binding?.playerButtonNext?.setOnClickListener(this)
        binding?.playerHide?.setOnClickListener(this)
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
        youtubePlayer?.loadVideos(getVideoIdList(), args.startIndex, 0)

        binding?.playerCurrentTime?.text = youtubePlayer?.currentTimeMillis?.toString()
        binding?.playerDurationTime?.text = youtubePlayer?.durationMillis?.toString()
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        result: YouTubeInitializationResult?
    ) {
        result?.let { error ->
            if (error.isUserRecoverableError) {
                error.getErrorDialog(activity, REQUEST_DIALOG_INT).show()
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
        currentPositionInPlaylist--
        updateTextViews(args.youtubeContent.list[currentPositionInPlaylist])
    }

    override fun onNext() {
        currentPositionInPlaylist++
        updateTextViews(args.youtubeContent.list[currentPositionInPlaylist])
    }

    override fun onLoaded(videoId: String?) {
        binding?.playerCurrentTime?.text =
            Utils.getStringTime(youtubePlayer?.currentTimeMillis) //TODO
        binding?.playerDurationTime?.text = Utils.getStringTime(youtubePlayer?.durationMillis)

        binding?.playerSeekbar?.progress = youtubePlayer?.currentTimeMillis!! //TODO
        binding?.playerSeekbar?.max = youtubePlayer?.durationMillis!!
        binding?.playerSeekbar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, b: Boolean) {}

            override fun onStartTrackingTouch(seekbar: SeekBar?) {}

            override fun onStopTrackingTouch(seekbar: SeekBar?) {
                youtubePlayer?.seekToMillis(seekbar?.progress!!)
            }
        })
    }

    override fun onPlaying() {
        binding?.playerButtonPlayPause?.setImageResource(R.drawable.ic_pause)
    }

    override fun onPaused() {
        binding?.playerButtonPlayPause?.setImageResource(R.drawable.ic_play)
    }

    override fun onStopped() {
        binding?.playerButtonPlayPause?.setImageResource(R.drawable.ic_play)
    }

    override fun onSeekTo(millis: Int) {
        binding?.playerCurrentTime?.text =
            Utils.getStringTime(youtubePlayer?.currentTimeMillis) //TODO
    }

    override fun onClick(view: View?) {
        when (view) {
            binding?.playerButtonPlayPause -> {
                if (youtubePlayer?.isPlaying == true) {
                    youtubePlayer?.pause()
                } else {
                    youtubePlayer?.play()
                }
            }
            binding?.playerButtonNext -> {
                if (youtubePlayer?.hasNext() == true) {
                    youtubePlayer?.next()
                } else {
                    showToast(R.string.player_end_of_queue)
                }
            }
            binding?.playerButtonPrev -> {
                if (youtubePlayer?.hasPrevious() == true) {
                    youtubePlayer?.previous()
                } else {
                    showToast(R.string.player_start_of_queue)
                }
            }
            binding?.playerHide -> TODO()
        }
    }

    private fun updateTextViews(item: YoutubeItem) {
        if (item is Song) {
            binding?.playerTitle?.text = item.snippet.title
            binding?.playerChannel?.text = item.snippet.videoOwnerChannelTitle
        } else if (item is SearchVideo) {
            binding?.playerTitle?.text = item.snippet.title
            binding?.playerChannel?.text = item.snippet.channelTitle
        }
    }

    private fun getVideoIdList(): List<String> {
        val list = ArrayList<String>()
        for (item in args.youtubeContent.list) {
            when (item) {
                is Song -> list.add(item.snippet.resourceId.videoId)
                is SearchVideo -> list.add(item.id.videoId)
            }
        }
        return list
    }

    private fun showToast(resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        private const val REQUEST_DIALOG_INT = 100
    }
}

package com.example.videopagerdemo.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.FrameLayout
import androidx.lifecycle.lifecycleScope
import com.example.videopagerdemo.databinding.FragmentPlayerBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [PlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayerFragment(private val videoUrl: String) : Fragment() {

    private lateinit var viewBinding: FragmentPlayerBinding

    private val mediaPlayer = MediaPlayer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewBinding = FragmentPlayerBinding.inflate(inflater, container, false)
        return viewBinding.root
//        return inflater.inflate(viewBinding.root, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaPlayer.apply {
            setOnPreparedListener {
                viewBinding.progressBarH.max = mediaPlayer.duration
                // it.start()
                seekTo(1)
                viewBinding.progressBar.visibility = View.INVISIBLE
            }
            setDataSource(videoUrl)
            prepareAsync()
            viewBinding.progressBar.visibility = View.VISIBLE
        }
        lifecycleScope.launch {
            //协程
            while (true) {
                viewBinding.progressBarH.progress = mediaPlayer.currentPosition
                delay(500)
            }
        }
        viewBinding.surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                mediaPlayer.setDisplay(holder)
                mediaPlayer.setScreenOnWhilePlaying(true)
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }

        })
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
        lifecycleScope.launch {
            //当第视频没有播放的时候让它播放，每500ms检测一次
            while (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                delay(500)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
    }
}
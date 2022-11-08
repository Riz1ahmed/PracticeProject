package com.example.testproject

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import com.example.testproject.databinding.ActivityVideoPlayerBinding

class VideoPlayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityVideoPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setVideo()
    }

    private var mp: MediaPlayer? = null
    private fun setVideo() {
        //prepare video
        binding.videoView.setOnPreparedListener {
            mp = it
            binding.seekBar.max = mp!!.duration
            Toast.makeText(this, "Video prepared", Toast.LENGTH_SHORT).show()
        }
        //set video
        binding.videoView.setVideoURI(uriFromRaw(R.raw.premium_features_overview_720p))
        //continue seekbar
        Handler(Looper.getMainLooper()).let { handler ->
            val runner = object : Runnable {
                override fun run() {
                    if (binding.videoView.isPlaying)
                        binding.seekBar.progress = binding.videoView.currentPosition
                    handler.removeCallbacks(this)
                    handler.postDelayed(this, 100)
                }
            }
            handler.postDelayed(runner, 100)
        }
        //set seek listener
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) binding.videoView.seekTo(progress)
            }
        })
        //play/stop button click
        binding.btnPlayStop.setOnClickListener {
            mp?.apply {
                binding.btnPlayStop.text =
                    if (!isPlaying) start().let { "STOP" }
                    else {
                        pause()
                        seekTo(0)
                        binding.seekBar.progress = 0
                        "PLAY"
                    }
            }
        }
        //resume/pause button click
        binding.btnResumePause.setOnClickListener {
            mp?.apply {
                binding.btnResumePause.text = if (isPlaying) "RESUME" else "PAUSE"
                if (isPlaying) pause() else start()
            }
        }
    }

    private fun uriFromRaw(@RawRes resId: Int) = Uri.parse("android.resource://$packageName/$resId")
}
package com.example.testproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRangeSeekbar.setOnClickListener { openActivity(RangeSeekbarActivity::class.java) }
        binding.btnVideoPlayer.setOnClickListener { openActivity(VideoPlayerActivity::class.java) }
        binding.btnVideoTimeline.setOnClickListener { openActivity(VideoTimelineActivity::class.java) }
        binding.btnSvgPathLoader.setOnClickListener { openActivity(CustomViewActivity::class.java) }
    }

    private fun openActivity(kClass: Class<*>) {
        startActivity(Intent(this, kClass))
    }
}
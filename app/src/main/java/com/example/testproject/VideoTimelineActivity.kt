package com.example.testproject

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.testproject.databinding.ActivityVideoTimelineBinding

class VideoTimelineActivity : AppCompatActivity() {
    lateinit var binding: ActivityVideoTimelineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoTimelineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sbSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.layoutBars.layoutParams =
                    LinearLayout.LayoutParams(progress, LinearLayout.LayoutParams.WRAP_CONTENT)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        controlAsDeviceWidth()
    }

    private fun controlAsDeviceWidth() {
        //set Screen w size
        val deviceW = resources.displayMetrics.widthPixels
        binding.sbSize.min = deviceW / 3
        binding.sbSize.max = deviceW * 3
        binding.sbSize.progress = deviceW
    }
}
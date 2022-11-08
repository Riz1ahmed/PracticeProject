package com.example.testproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testproject.databinding.ActivityRangeSeekbarBinding
import java.text.NumberFormat
import java.util.*

class RangeSeekbarActivity : AppCompatActivity() {
    lateinit var binding: ActivityRangeSeekbarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRangeSeekbarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.continuousRangeSlider.addOnChangeListener { slider, value, fromUser ->

        }
        binding.continuousRangeSlider.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("BDT")
            format.format(value.toDouble())
        }
    }
}
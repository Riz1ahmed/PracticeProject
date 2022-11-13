package com.example.testproject

import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caverock.androidsvg.SVG
import com.example.testproject.databinding.ActivityCustomViewBinding
import com.example.testproject.utilsAndData.GSonUtils
import com.example.testproject.utilsAndData.logD
import com.example.testproject.utilsAndData.model.FigmaJs
import com.example.testproject.utilsAndData.model.figmaModel.FigmaJson

class CustomViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityCustomViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val json = GSonUtils.fromJson<FigmaJson>(FigmaJs.figmaSample)
        logD("Json: $json")

        loadSvg()
    }

    private fun loadSvg() {
        val svg = SVG.getFromAsset(assets, "svg/recepie.svg")
        val pd = PictureDrawable(svg.renderToPicture())
        binding.imageView.setImageDrawable(pd)
    }
}
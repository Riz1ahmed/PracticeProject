package com.example.testproject

import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caverock.androidsvg.SVG
import com.example.testproject.databinding.ActivityCustomViewBinding
import com.example.testproject.utilsAndData.data.FigmaJs
import com.example.testproject.utilsAndData.data.ImageBase64Str
import com.example.testproject.utilsAndData.logD
import com.example.testproject.utilsAndData.model.figmaModel.FigmaJson
import com.learner.codereducer.utils.BitmapTools
import com.learner.codereducer.utils.GSonUtils

class CustomViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityCustomViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val figmaJson = GSonUtils.fromJson<FigmaJson>(FigmaJs.figmaSample)
        //logD("Json: $figmaJson")
        loadAssetJson()
        binding.customView.setJsonInvalidate(figmaJson)

        loadSvg()

        loadBase64Image()
    }

    private fun loadBase64Image() {
        val bp = BitmapTools.getBitmapFromBase64Str(ImageBase64Str.logo)
        binding.imgFromBase64.setImageBitmap(bp)
    }

    private fun loadAssetJson() {
        val str = GSonUtils.loadJsonFromAsset(assets, "json/FigmaJson.js")
        val figmaJson = GSonUtils.fromJson<FigmaJson>(str)
        logD("FigmaJson: $figmaJson")
        //logD("JsonStr: $str")
    }

    private fun loadSvg() {
        val svg = SVG.getFromAsset(assets, "svg/recipe.svg")
        val pd = PictureDrawable(svg.renderToPicture())
        binding.imageView.setImageDrawable(pd)
    }
}
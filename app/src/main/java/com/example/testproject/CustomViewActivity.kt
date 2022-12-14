package com.example.testproject

import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.caverock.androidsvg.SVG
import com.example.testproject.databinding.ActivityCustomViewBinding
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
        loadBase64Image()

        loadCustomView()


        loadSvgBy_androidsvg()
    }

    private fun loadCustomView() {
        //val figmaJson = GSonUtils.fromJson<FigmaJson>(FigmaJs.figmaSample)
        //logD("Json: $figmaJson")
        val jsonStr = loadJsonFromAsset("json/sample.js")
        binding.customView.setJsonInvalidate(jsonStr)

        binding.sbScaleCustomView.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val scale = progress.toFloat() / 100
                binding.customView.scaleX = scale
                binding.customView.scaleY = scale
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun loadBase64Image() {
        val bp = BitmapTools.getBitmapFromBase64Str(ImageBase64Str.logo)
        binding.imgFromBase64.setImageBitmap(bp)
    }

    /**
     * Load figma json.
     * @param path Contain folders & extension. eg: "json/FigmaJson.js"*/
    private fun loadJsonFromAsset(path: String): FigmaJson {
        val str = GSonUtils.loadJsonFromAsset(assets, path)
        val figmaJson = GSonUtils.fromJson<FigmaJson>(str)
        logD("FigmaJson: $figmaJson")
        return figmaJson
    }

    private fun loadSvgBy_androidsvg() {
        val svg = SVG.getFromAsset(assets, "svg/crypto.svg")
        val pd = PictureDrawable(svg.renderToPicture())
        binding.imageView.setImageDrawable(pd)
    }
}
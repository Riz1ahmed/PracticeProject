package com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage.children.fillX

import com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage.children.ColorRGBA
import com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage.children.fillX.gradientStop.GradientStop

data class FillX(
    val blendMode: String,
    val color: ColorRGBA,
    val type: String,
    //for Gradient
    val gradientHandlePositions: List<GradientHandlePosition>? = null,
    val gradientStops: List<GradientStop>? = null,
)
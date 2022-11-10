package com.example.testproject.utilsAndData.model.figmaModel

data class FillX(
    val blendMode: String,
    val color: ColorX,
    val type: String,
    //Gradient
    val gradientHandlePositions: List<GradientHandlePosition>? = null,
    val gradientStops: List<GradientStop>? = null,
)
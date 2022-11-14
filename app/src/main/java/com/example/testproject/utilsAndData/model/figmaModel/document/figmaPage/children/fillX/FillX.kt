package com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage.children.fillX

import com.example.testproject.utilsAndData.data.VectorDrawConst
import com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage.children.ColorRGBA
import com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage.children.fillX.gradientStop.GradientStop

data class FillX(
    val blendMode: String,
    /** These constrains inside [VectorDrawConst.FillType]*/
    val type: String,
    /**Available if this is [VectorDrawConst.FillType.SOLID]*/
    val color: ColorRGBA?,
    /**Available if this is [VectorDrawConst.FillType.GRADIENT_RADIAL]*/
    val gradientHandlePositions: List<GradientHandlePosition>? = null,
    val gradientStops: List<GradientStop>? = null,
)
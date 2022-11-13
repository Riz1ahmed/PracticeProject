package com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage.children

import com.example.testproject.utilsAndData.model.figmaModel.SizeX
import com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage.children.fillX.FillX

//Frame. Type: FRAME.
//Group. Type: Group
//Item. Type: Vector
data class Children(
    val absoluteBoundingBox: AbsoluteBoundingBox,
    val absoluteRenderBounds: AbsoluteRenderBounds,
    /**Available if it's */
    val background: List<Background>? = null,
    val backgroundColor: ColorRGBA? = null,
    val blendMode: String,
    val children: List<Children>,
    val clipsContent: Boolean,
    val constraints: ConstraintsX,
    val effects: List<Any>,
    /**Available if It's item*/
    val fillGeometry: List<FillGeometry>? = null,
    val fills: List<FillX>,
    val id: String,
    val name: String,
    /**
     * X Transform value from 1st of 3rd value
     * Y Transform value from 2st of 3rd value
     * So,
     * ```
     *      xTransform = this[0][2]
     *      yTransform = this[1][2]
     * ```
     */
    val relativeTransform: List<List<Double>>,
    val scrollBehavior: String,
    val size: SizeX,
    val strokeAlign: String,
    /**When contain stroke*/
    val strokeGeometry: List<StrokeGeometry>? = null,
    val strokeWeight: Int,
    val strokes: List<Any>,
    val type: String
)

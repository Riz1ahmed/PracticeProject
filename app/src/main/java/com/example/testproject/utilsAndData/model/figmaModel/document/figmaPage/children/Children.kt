package com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage.children

import android.graphics.PointF
import com.example.testproject.utilsAndData.data.VectorDrawConst
import com.example.testproject.utilsAndData.logD
import com.example.testproject.utilsAndData.model.figmaModel.SizeX
import com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage.children.fillX.FillX
import com.example.testproject.utilsAndData.toColorInt
import kotlin.math.abs

//Frame. Type: FRAME.
//Group. Type: Group
//Item. Type: Vector
data class Children(
    val absoluteBoundingBox: AbsoluteBoundingBox,
    val absoluteRenderBounds: AbsoluteRenderBounds,
    /**Available if it's [VectorDrawConst.ChildType.VECTOR] type*/
    val background: List<Background>? = null,
    val backgroundColor: ColorRGBA? = null,
    val blendMode: String,
    val children: List<Children>,
    val clipsContent: Boolean,
    val constraints: ConstraintsX,
    val effects: List<Any>,
    /**Available if it's [VectorDrawConst.ChildType.VECTOR] type*/
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
    /**These constraints inside [VectorDrawConst.ChildType]*/
    val type: String
) {
    /**Call only if this is [VectorDrawConst.ChildType.VECTOR] type*/
    fun get1stSolidBgColor() = fills[0].color?.let { get255BaseColor(it) }

    /**Call only if this is [VectorDrawConst.ChildType.VECTOR] type*/
    fun get1stGeometricPath() = fillGeometry?.get(0)?.path

    fun getTLMargin() = PointF(relativeTransform[0][2].toFloat(), relativeTransform[1][2].toFloat())
    fun getTLMargin(parentAbsPos: AbsoluteBoundingBox) =
        PointF(
            abs(parentAbsPos.x - absoluteBoundingBox.x).toFloat(),
            abs(parentAbsPos.y - absoluteBoundingBox.y).toFloat()
        )

    private fun get255BaseColor(colorRGBA: ColorRGBA): Int {
        logD("colorRGBA: $colorRGBA")
        with(colorRGBA) { return toColorInt(a, r, g, b) }
        //with(colorRGBA) { return Color.argb(a.toFloat(), r.toFloat(), g.toFloat(), b.toFloat()) }
    }

}

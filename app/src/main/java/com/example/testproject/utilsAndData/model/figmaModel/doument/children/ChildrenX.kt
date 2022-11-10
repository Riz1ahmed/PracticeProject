package com.example.testproject.utilsAndData.model.figmaModel.doument.children

import com.example.testproject.utilsAndData.model.figmaModel.*

//Frame. Type: FRAME.
//Group. Type: Group
//Item. Type: Vector
data class ChildrenX(
    val absoluteBoundingBox: AbsoluteBoundingBox,
    val absoluteRenderBounds: AbsoluteRenderBounds,
    /**Available if it's */
    val background: List<Background>?=null,
    val backgroundColor: BackgroundColor?=null,
    val blendMode: String,
    val children: List<ChildrenX>,
    val clipsContent: Boolean,
    val constraints: ConstraintsX,
    val effects: List<Any>,
    /**Available if It's item*/
    val fillGeometry: List<FillGeometry>?=null,
    val fills: List<FillX>,
    val id: String,
    val name: String,
    val relativeTransform: List<List<Double>>,
    val scrollBehavior: String,
    val size: SizeX,
    val strokeAlign: String,
    /**When contain stroke*/
    val strokeGeometry: List<Any>?=null,
    val strokeWeight: Int,
    val strokes: List<Any>,
    val type: String
)
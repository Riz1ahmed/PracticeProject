package com.example.testproject.utilsAndData.data

object VectorDrawConst {

    object ChildType {
        /**Figma root file or A page*/
        const val CANVAS = "CANVAS"

        /**Screen which contain a # as prefix */
        const val FRAME = "FRAME"
        const val GROUP = "GROUP"

        /**contain fillGeometry value*/
        const val VECTOR = "VECTOR"
    }

    object FillType {
        const val SOLID = "SOLID"
        const val GRADIENT_RADIAL = "GRADIENT_RADIAL"
    }
}
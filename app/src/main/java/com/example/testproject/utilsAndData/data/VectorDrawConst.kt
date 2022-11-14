package com.example.testproject.utilsAndData.data

object VectorDrawConst {
    object PathTypes {
        const val move = 'M'
        const val rMove = 'm'

        const val line = 'L'
        const val rLine = 'l'
        const val hLine = 'H'
        const val rHLine = 'h'
        const val vLine = 'V'
        const val rVLine = 'v'

        const val cubic = 'C'
        const val rCubic = 'c'
        const val smoothCubic = 'S'
        const val rSmoothCubic = 's'

        const val quad = 'Q'
        const val rQuad = 'q'
        const val smoothQuad = 'T'
        const val rSmoothQuad = 't'

        const val arc = 'A'
        const val rArc = 'a'

        const val stop = 'Z'
        const val rStop = 'z'//There hasn't any r. but available in upperCase
    }

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
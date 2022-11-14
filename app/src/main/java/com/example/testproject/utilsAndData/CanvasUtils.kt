package com.example.testproject.utilsAndData

import android.graphics.Path
import android.graphics.PointF
import com.example.testproject.utilsAndData.data.VectorDrawConst

object CanvasUtils {

    /*fun addDataToPath(path: Path, typeWithData: String, icScale: Int) {
        val type = typeWithData.first()
        addDataToPath(path, type, typeWithData.drop(1).split(" ").map { it.toFloat() * icScale })
    }*/

    private fun addDataToPath(path: Path, type: Char, data: List<Float>, absPos: PointF) {
        //logD("type=$type, data=$data")
        when (type) {
            VectorDrawConst.PathTypes.move ->
                path.moveTo(data[0] + absPos.x, data[1] + absPos.y)
            VectorDrawConst.PathTypes.line ->
                path.lineTo(data[0] + absPos.x, data[1] + absPos.y)
            VectorDrawConst.PathTypes.hLine ->
                path.lineTo(data[0] + absPos.x, 0f + absPos.y)//not 0 will be last pos
            VectorDrawConst.PathTypes.vLine ->
                path.lineTo(0f + absPos.x, data[0] + absPos.y)//not 0 will be last pos
            VectorDrawConst.PathTypes.cubic ->
                path.cubicTo(
                    data[0] + absPos.x, data[1] + absPos.y,
                    data[2] + absPos.x, data[3] + absPos.y,
                    data[4] + absPos.x, data[5] + absPos.y
                )
            //PathTypes.smoothCubic -> path.rCubicTo(oldQValue,lastQValu,data[0],data[1])
            VectorDrawConst.PathTypes.quad -> path.quadTo(
                data[0] + absPos.x, data[1] + absPos.y,
                data[2] + absPos.x, data[3] + absPos.y
            )
            //PathTypes.smoothQuad -> path.rQuadTo(oldQValue,lastQValu,data[0], data[1])
            //PathTypes.arc -> path.arcTo(0f, 0f, 50f, 80f, 0f, 45f, true)
            VectorDrawConst.PathTypes.stop -> path.close()

            VectorDrawConst.PathTypes.rMove ->
                path.rMoveTo(data[0] + absPos.x, data[1] + absPos.y)
            VectorDrawConst.PathTypes.rLine ->
                path.rLineTo(data[0] + absPos.x, data[1] + absPos.y)
            VectorDrawConst.PathTypes.rHLine -> path.rLineTo(data[0] + absPos.x, 0f)
            VectorDrawConst.PathTypes.rVLine -> path.rLineTo(0f, data[0] + absPos.y)
            VectorDrawConst.PathTypes.rCubic ->
                path.rCubicTo(
                    data[0] + absPos.x, data[1] + absPos.y,
                    data[2] + absPos.x, data[3] + absPos.y,
                    data[4] + absPos.x, data[5] + absPos.y
                )
            //PathTypes.rSmoothCubic -> path.rCubicTo(oldQValue,lastQValu,data[0],data[1])
            VectorDrawConst.PathTypes.rQuad -> path.rQuadTo(
                data[0] + absPos.x, data[1] + absPos.y,
                data[2] + absPos.x, data[3] + absPos.y
            )
            //PathTypes.rSmoothQuad -> path.rQuadTo(oldQValue,lastQValu,data[0], data[1])
            //PathTypes.rArc -> path.arcTo()
            VectorDrawConst.PathTypes.rStop -> path.close()
        }
    }

    /**Pass a SVG/XML/Figma path data and get a [Path] object contain this data */
    private fun splitPathData(fullPathData: String): ArrayList<Pair<Char, List<Float>>> {
        /**Replace multi space with single space*/
        val curData = fullPathData
            .replace("\\s+".toRegex(), " ")//Replace multiple space
            .replace(",", " ")//Replace comma with space

        val pathList = arrayListOf<Pair<Char, List<Float>>>()
        var i = 0
        for (j in curData.indices) {
            if (isPathTypeLatter(curData[j])) {
                var data = curData.substring(i, j)
                //logD("splitting Data: $data")
                if (data.isEmpty()) continue//for 1st time / every new path (z)

                val type = data.first()

                if (type.uppercase()[0] == VectorDrawConst.PathTypes.stop)
                    pathList.add(Pair(type, emptyList()))
                else {
                    data = data.drop(1) //It's type latter

                    //Remove first & last spaces if available
                    while (data.first() == ' ') data = data.drop(1)
                    while (data.last() == ' ') data = data.dropLast(1)

                    pathList.add(Pair(type, data.split(" ").map { it.toFloat() }))
                }
                i = j
            }
        }
        pathList.add(Pair('Z', listOf()))
        return pathList
    }

    private fun isPathTypeLatter(c: Char) = "MmLlHhVvCcSsQqTtAaZz".contains(c)

    fun getPathFromPathData(pathList: List<String>, itemsAbsPos: List<PointF>): Path {
        val path = Path()
        pathList.forEachIndexed { index, pathData ->
            val subPath = getPathFromPathData(pathData, itemsAbsPos[index])
            path.addPath(subPath)
        }
        return path
    }

    fun getPathFromPathData(pathData: String, absPos: PointF): Path {
        val path = Path()
        val dataList = splitPathData(pathData)
        dataList.forEach { data ->
            addDataToPath(path, data.first, data.second, absPos)
        }
        path.close()
        return path
    }
}
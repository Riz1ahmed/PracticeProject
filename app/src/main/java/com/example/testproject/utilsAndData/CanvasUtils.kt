package com.example.testproject.utilsAndData

import android.graphics.Path
import android.graphics.PointF
import com.example.testproject.utilsAndData.data.SVGPath

object CanvasUtils {

    /*fun addDataToPath(path: Path, typeWithData: String, icScale: Int) {
        val type = typeWithData.first()
        addDataToPath(path, type, typeWithData.drop(1).split(" ").map { it.toFloat() * icScale })
    }*/

    /**
     * @param type any latter from [SVGPath.PathTypes]
     * @param data this path data
     * @param absPos if want to move from current position, pass here the left and right margin values
     * @param lastPos This value must be pass if current [type] is Cubic or Quad.
     * */
    private fun addDataToPath(
        path: Path, svgPath: SVGPath, absPos: PointF, lastPos: PointF
    ) {
        svgPath.toPath(absPos, lastPos, path)
        //path.addPath(svgPath.toPath(absPos, lastPos))
        //logD("type=$type, data=$data")
        /*when (type) {
            SVGPath.PathTypes.move ->
                path.moveTo(data[0] + absPos.x, data[1] + absPos.y)
            SVGPath.PathTypes.line ->
                path.lineTo(data[0] + absPos.x, data[1] + absPos.y)
            SVGPath.PathTypes.hLine ->
                path.lineTo(data[0] + absPos.x, 0f + absPos.y)//not 0 will be last pos
            SVGPath.PathTypes.vLine ->
                path.lineTo(0f + absPos.x, data[0] + absPos.y)//not 0 will be last pos
            SVGPath.PathTypes.cubic ->
                path.cubicTo(
                    data[0] + absPos.x, data[1] + absPos.y,
                    data[2] + absPos.x, data[3] + absPos.y,
                    data[4] + absPos.x, data[5] + absPos.y
                )
            //PathTypes.smoothCubic -> path.rCubicTo(oldQValue,lastQValu,data[0],data[1])
            SVGPath.PathTypes.quad -> path.quadTo(
                data[0] + absPos.x, data[1] + absPos.y,
                data[2] + absPos.x, data[3] + absPos.y
            )
            //PathTypes.smoothQuad -> path.rQuadTo(oldQValue,lastQValu,data[0], data[1])
            //PathTypes.arc -> path.arcTo(0f, 0f, 50f, 80f, 0f, 45f, true)
            SVGPath.PathTypes.stop -> path.close()

            SVGPath.PathTypes.rMove ->
                path.rMoveTo(data[0] + absPos.x, data[1] + absPos.y)
            SVGPath.PathTypes.rLine ->
                path.rLineTo(data[0] + absPos.x, data[1] + absPos.y)
            SVGPath.PathTypes.rHLine -> path.rLineTo(data[0] + absPos.x, 0f)
            SVGPath.PathTypes.rVLine -> path.rLineTo(0f, data[0] + absPos.y)
            SVGPath.PathTypes.rCubic -> SVGPathUtil.arcTo(
                lastX = lastPos!!.x, lastY = lastPos.y,
                rx = data[0], ry = data[0],
                angle = data[0],
                largeArcFlag = data[0].toBoolean(), sweepFlag = data[0].toBoolean(),
                x = data[0], y = data[0],
                path = path
            )
            *//*path.rCubicTo(
                data[0] + absPos.x, data[1] + absPos.y,
                data[2] + absPos.x, data[3] + absPos.y,
                data[4] + absPos.x, data[5] + absPos.y
            )*//*
            //PathTypes.rSmoothCubic -> path.rCubicTo(oldQValue,lastQValu,data[0],data[1])
            SVGPath.PathTypes.rQuad -> path.rQuadTo(
                data[0] + absPos.x, data[1] + absPos.y,
                data[2] + absPos.x, data[3] + absPos.y
            )
            //PathTypes.rSmoothQuad -> path.rQuadTo(oldQValue,lastQValu,data[0], data[1])
            SVGPath.PathTypes.rArc -> SVGPathUtil.arcTo(
                lastX = lastPos!!.x, lastY = lastPos.y,
                rx = data[0], ry = data[0],
                angle = data[0],
                largeArcFlag = data[0].toBoolean(), sweepFlag = data[0].toBoolean(),
                x = data[0], y = data[0],
                path = path
            )
            SVGPath.PathTypes.rStop -> path.close()
        }*/
    }

    /**Pass a SVG/XML/Figma path data and get a [Path] object contain this data */
    private fun splitPathData(fullPathData: String): ArrayList<SVGPath> {
        /**Replace multi space with single space*/
        val curData = fullPathData
            .replace("\\s+".toRegex(), " ")//Replace multiple space
            .replace(",", " ")//Replace comma with space

        val pathList = arrayListOf<SVGPath>()
        var i = 0
        for (j in curData.indices) {
            if (isPathTypeLatter(curData[j])) {
                var data = curData.substring(i, j)
                //logD("splitting Data: $data")
                if (data.isEmpty()) continue//for 1st time / every new path (z)

                val type = data.first()

                if (type.uppercase()[0] == SVGPath.PathTypes.stop)
                    pathList.add(SVGPath(type, emptyList()))
                else {
                    data = data.drop(1) //It's type latter

                    //Remove first & last spaces if available
                    while (data.first() == ' ') data = data.drop(1)
                    while (data.last() == ' ') data = data.dropLast(1)

                    pathList.add(SVGPath(type, data.split(" ").map { it.toFloat() }))
                }
                i = j
            }
        }
        pathList.add(SVGPath('Z', listOf()))
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
        //var last = PointF(dataList[0].data[0], dataList[0].data[1])
        var last = PointF(0f, 0f)
        dataList.forEach { data ->
            addDataToPath(path, data, absPos, last)
            //Remember last position
            if (!(data.type == 'z' || data.type == 'Z'))
                data.data.let {
                    last = PointF(it[it.size - 2], it[it.size - 1])
                }
        }
        path.close()
        return path
    }
}
package com.example.testproject.utilsAndData

import android.graphics.Path
import android.graphics.PointF

object CanvasUtils {

    /*fun addDataToPath(path: Path, typeWithData: String, icScale: Int) {
        val type = typeWithData.first()
        addDataToPath(path, type, typeWithData.drop(1).split(" ").map { it.toFloat() * icScale })
    }*/

    private fun addDataToPath(path: Path, type: Char, data: List<Float>, absPos: PointF) {
        logD("type=$type, data=$data")
        when (type) {
            CustomView.Companion.PathTypes.move -> path.moveTo(
                data[0] + absPos.x, data[1] + absPos.y
            )
            CustomView.Companion.PathTypes.line -> path.lineTo(
                data[0] + absPos.x, data[1] + absPos.y
            )
            CustomView.Companion.PathTypes.hLine -> path.lineTo(
                data[0] + absPos.x, 0f + absPos.y
            )//not 0 will be last pos
            CustomView.Companion.PathTypes.vLine -> path.lineTo(
                0f + absPos.x, data[0] + absPos.y
            )//not 0 will be last pos
            CustomView.Companion.PathTypes.cubic ->
                path.cubicTo(
                    data[0] + absPos.x, data[1] + absPos.y,
                    data[2] + absPos.x, data[3] + absPos.y,
                    data[4] + absPos.x, data[5] + absPos.y
                )
            //PathTypes.smoothCubic -> path.rCubicTo(oldQValue,lastQValu,data[0],data[1])
            CustomView.Companion.PathTypes.quad -> path.quadTo(
                data[0] + absPos.x, data[1] + absPos.y,
                data[2] + absPos.x, data[3] + absPos.y
            )
            //PathTypes.smoothQuad -> path.rQuadTo(oldQValue,lastQValu,data[0], data[1])
            //PathTypes.arc -> path.arcTo(0f, 0f, 50f, 80f, 0f, 45f, true)
            CustomView.Companion.PathTypes.stop -> path.close()

            CustomView.Companion.PathTypes.rMove -> path.rMoveTo(
                data[0] + absPos.x, data[1] + absPos.y
            )
            CustomView.Companion.PathTypes.rLine -> path.rLineTo(
                data[0] + absPos.x, data[1] + absPos.y
            )
            CustomView.Companion.PathTypes.rHLine -> path.rLineTo(data[0] + absPos.x, 0f)
            CustomView.Companion.PathTypes.rVLine -> path.rLineTo(0f, data[0] + absPos.y)
            CustomView.Companion.PathTypes.rCubic ->
                path.rCubicTo(
                    data[0] + absPos.x, data[1] + absPos.y,
                    data[2] + absPos.x, data[3] + absPos.y,
                    data[4] + absPos.x, data[5] + absPos.y
                )
            //PathTypes.rSmoothCubic -> path.rCubicTo(oldQValue,lastQValu,data[0],data[1])
            CustomView.Companion.PathTypes.rQuad -> path.rQuadTo(
                data[0] + absPos.x, data[1] + absPos.y,
                data[2] + absPos.x, data[3] + absPos.y
            )
            //PathTypes.rSmoothQuad -> path.rQuadTo(oldQValue,lastQValu,data[0], data[1])
            //PathTypes.rArc -> path.arcTo()
            CustomView.Companion.PathTypes.rStop -> path.close()
        }
    }

    fun splitPathData(fullData: String): ArrayList<Pair<Char, List<Float>>> {
        /**Replace multi space with single space*/
        //regen except later


        val curData = fullData
            .replace("\\s+".toRegex(), " ")//Replace multiple space
            .replace(",", " ")//Replace comma with space

        val paths = arrayListOf<Pair<Char, List<Float>>>()
        var i = 0
        for (j in curData.indices) {
            if (isPathTypeLatter(curData[j])) {
                var data = curData.substring(i, j)
                logD("splitting Data: $data")
                if (data.isEmpty()) continue//for 1st time / every new path (z)

                val type = data.first()

                if (type.uppercase()[0] == CustomView.Companion.PathTypes.stop)
                    paths.add(Pair(type, emptyList()))
                else {
                    //It's type latter
                    data = data.drop(1)
                    //Remove first & last space if available
                    if (data.first() == ' ') data = data.drop(1)
                    if (data.last() == ' ') data = data.dropLast(1)

                    paths.add(Pair(type, data.split(" ").map { it.toFloat() }))
                }
                i = j
            }
        }
        paths.add(Pair('Z', listOf()))
        return paths
    }

    private fun isPathTypeLatter(c: Char) = "MmLlHhVvCcSsQqTtAaZz".contains(c)

    fun getPathData(pathList: List<String>, itemsAbsPos: List<PointF>, canvasWidth: Int): Path {
        val path = Path()
        //val icScale = canvasWidth / pathWidth
        pathList.forEachIndexed { index, pathData ->
            drawPath(path, pathData, itemsAbsPos[index])
        }
        //pathList.forEach { drawPath(path, it, icScale) }
        return path
    }

    private fun drawPath(path: Path, pathData: String, absPos: PointF) {
        //logD("drawPath: $pathData")
        //icScale = canvasSize.width / viewPortSize.width

        val dataList = splitPathData(pathData)
        dataList.forEach { data ->
            addDataToPath(path, data.first, data.second, absPos)
        }
        path.close()
    }
}
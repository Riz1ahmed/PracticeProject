package com.example.testproject.utilsAndData

import android.graphics.Path

object CanvasUtils {

    /*fun addDataToPath(path: Path, typeWithData: String, icScale: Int) {
        val type = typeWithData.first()
        addDataToPath(path, type, typeWithData.drop(1).split(" ").map { it.toFloat() * icScale })
    }*/

    fun addDataToPath(path: Path, type: Char, data: List<Float>) {
        logD("type=$type, data=$data")
        when (type) {
            CustomView.Companion.PathTypes.move -> path.moveTo(data[0], data[1])
            CustomView.Companion.PathTypes.line -> path.lineTo(data[0], data[1])
            CustomView.Companion.PathTypes.hLine -> path.lineTo(data[0], 0f)//not 0 will be last pos
            CustomView.Companion.PathTypes.vLine -> path.lineTo(0f, data[0])//not 0 will be last pos
            CustomView.Companion.PathTypes.cubic ->
                path.cubicTo(data[0], data[1], data[2], data[3], data[4], data[5])
            //PathTypes.smoothCubic -> path.rCubicTo(oldQValue,lastQValu,data[0],data[1])
            CustomView.Companion.PathTypes.quad -> path.quadTo(data[0], data[1], data[2], data[3])
            //PathTypes.smoothQuad -> path.rQuadTo(oldQValue,lastQValu,data[0], data[1])
            //PathTypes.arc -> path.arcTo(0f, 0f, 50f, 80f, 0f, 45f, true)
            CustomView.Companion.PathTypes.stop -> path.close()

            CustomView.Companion.PathTypes.rMove -> path.rMoveTo(data[0], data[1])
            CustomView.Companion.PathTypes.rLine -> path.rLineTo(data[0], data[1])
            CustomView.Companion.PathTypes.rHLine -> path.rLineTo(data[0], 0f)
            CustomView.Companion.PathTypes.rVLine -> path.rLineTo(0f, data[0])
            CustomView.Companion.PathTypes.rCubic ->
                path.rCubicTo(data[0], data[1], data[2], data[3], data[4], data[5])
            //PathTypes.rSmoothCubic -> path.rCubicTo(oldQValue,lastQValu,data[0],data[1])
            CustomView.Companion.PathTypes.rQuad -> path.rQuadTo(data[0], data[1], data[2], data[3])
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
            if (isPathLatter(curData[j])) {
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

    private fun isPathLatter(c: Char) = "MmLlHhVvCcSsQqTtAaZz".contains(c)

    fun getPathData(pathList: List<String>, pathWidth: Int, canvasWidth: Int): Path {
        val path = Path()
        val icScale = canvasWidth / pathWidth
        pathList.forEach { drawPath(path, it, icScale) }
        return path
    }

    fun drawPath(path: Path, pathData: String, icScale: Int) {
        //logD("drawPath: $pathData")
        //icScale = canvasSize.width / viewPortSize.width

        val dataList = splitPathData(pathData)
        dataList.forEach { data ->
            addDataToPath(path, data.first, data.second.map { it * icScale })
        }
        path.close()
    }
}
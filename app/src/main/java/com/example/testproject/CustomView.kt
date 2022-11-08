package com.example.testproject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.ColorDrawable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.util.Size
import android.view.View

class CustomView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val scaleSize = 10
    private val canvasSize = Size(108 * scaleSize, 108 * scaleSize)
    private val bg = ColorDrawable(Color.YELLOW)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 5f
        color = Color.RED
        style = Paint.Style.STROKE
    }
    private val textPaint = TextPaint().also {
        it.color = Color.RED
        it.strokeWidth = 20f
        it.textSize = 100f
    }
    private val path = Path()

    init {
        background = bg
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //canvas?.drawCircle(40f, 40f, 40f, paint)

        //canvas?.drawText("$width x $height", 0f, 100f, textPaint)


        drawPathData(canvas!!)
    }

    private fun drawPathData(canvas: Canvas) {

        //drawPath()


        /*val mData= listOf(
            Pair('M', listOf(68.5F, 35.0F))
        )*/
        /*path.moveTo(68.5F, 35.0F)
        path.lineTo(78.2664F, 65.0578F)
        path.lineTo(109.871F, 65.0578F)
        path.lineTo(84.3023F, 83.6345F)
        path.lineTo(94.0687F, 113.692F)
        path.lineTo(68.5F, 95.1155F)
        path.lineTo(42.9313F, 113.692F)
        path.lineTo(52.6977F, 83.6345F)
        path.lineTo(27.129F, 65.0578F)
        path.lineTo(58.7336F, 65.0578F)
        path.lineTo(68.5F, 35F)
        path.close()*/

        /*path.moveTo(5f, 5f)
        path.lineTo(20f, 5f)
        path.lineTo(20f, 20f)
        path.lineTo(5f, 20f)

        path.moveTo(0f, 0f)
        path.rLineTo(3f, 0f)
        path.rLineTo(0f, 3f)
        path.rLineTo(-3f, 0f)
        path.close()*/

        var testData = "M8 5C6 5 6 5 6 7L6 17C6 18 6 19 8 19C9 19 10 18 10 17L10 7C10 5 9 5 8 5 Z"
        testData += "M16 5C14 5 14 5 14 7L14 17C14 18 14 19 16 19C17 19 18 18 18 17L18 7C18 5 17 5 16 5z"
        //"M8 5C6 5 6 5 6 7L6 17C6 18 6 19 8 19C9 19 10 18 10 17L10 7C10 5 9 5 8 5"
        //"z" +
        //"M 16 5" +
        //"C14 5 14 5 14 7" +
//                "L14 17" +
//                "C14 18 14 19 16 19" +
//                "C17 19 18 18 18 17" +
//                "L18 7" +
//                "C18 5 17 5 16 5" +
//                "z"
        //""

        drawPath(androidLogoData)

        /*path.apply {
            addPath("M8 5")
            addPath("C6 5 6 5 6 7")
            addPath("L6 17")
            addPath("C6 18 6 19 8 19")
            addPath("C9 19 10 18 10 17")
            addPath("C10 5 9 5 8 5")
            close()
        }*/

        canvas.drawPath(path, paint)
    }

    private fun drawPath(pathData: String) {
        Log.d("xyz", "drawPath: $pathData")
        val dataList = splitPathData(pathData)
        dataList.forEach { data ->
            addPath(data.first, data.second.map { it * scaleSize })
        }
        path.close()
    }


    private fun addPath(typeWithData: String) {
        val type = typeWithData.first()
        addPath(type, typeWithData.drop(1).split(" ").map { it.toFloat() * scaleSize })
    }

    private fun addPath(type: Char, data: List<Float>) {
        logD("type=$type, data=$data")
        when (type) {
            'm' -> path.rMoveTo(data[0], data[1])
            'l' -> path.rMoveTo(data[0], data[1])
            'c' -> path.rCubicTo(data[0], data[1], data[2], data[3], data[4], data[5])

            'M' -> path.moveTo(data[0], data[1])
            'L' -> path.lineTo(data[0], data[1])
            'C' -> path.cubicTo(data[0], data[1], data[2], data[3], data[4], data[5])
        }
    }
    /*fun Path.cubicTo(pathData: String) {
        val data = pathData.split(" ").map { it.toFloat() * 10 }
        Log.d("xyz", "cubicTo: $data")
        path.cubicTo(data[0], data[1], data[2], data[3], data[4], data[5])
    }*/

    private fun splitPathData(fullData: String): ArrayList<Pair<Char, List<Float>>> {
        /**Replace multi space with single space*/
        val curData = fullData.replace("\\s+".toRegex(), " ")

        val paths = arrayListOf<Pair<Char, List<Float>>>()
        var i = 0
        for (j in curData.indices) {
            if (curData[j].isLetter()) {
                var data = curData.substring(i, j)
                logD("splitting Data: $data")
                if (data.isEmpty()) continue//for 1st time / every new path (z)

                val type = data.first()

                if (type.uppercase()[0] == PathTypes.stop)
                    paths.add(Pair(type, emptyList()))
                else {
                    //It's type latter
                    data = data.drop(1)
                    //Remove first & last space if available
                    if (data.first() == ' ') data = data.drop(1)
                    if (data.last() == ' ') data = data.dropLast(1)

                    paths.add(Pair(type, data.split(" ",",").map { it.toFloat() }))
                }
                i = j
            }
        }
        paths.add(Pair('Z', listOf()))
        paths.forEach { println(it) }
        return paths
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //layoutParams = ViewGroup.LayoutParams(400, 4000)
        setMeasuredDimension(canvasSize.width, canvasSize.height)
    }

    companion object {

        //viewPort 150x150
        private const val starPath =
            "M68.5 35L78.2664 65.0578L109.871 65.0578L84.3023 83.6345L94.0687 113.692L68.5 95.1155L42.9313 113.692L52.6977 83.6345L27.129 65.0578L58.7336 65.0578L68.5 35Z"

        //viewPort 350x340
        private const val arrowPath =
            "M220.061 293.061C220.646 292.475 220.646 291.525 220.061 290.939L210.515 281.393C209.929 280.808 208.979 280.808 208.393 281.393C207.808 281.979 207.808 282.929 208.393 283.515L216.879 292L208.393 300.485C207.808 301.071 207.808 302.021 208.393 302.607C208.979 303.192 209.929 303.192 210.515 302.607L220.061 293.061ZM142 293.5H219V290.5H142V293.5Z"

        //ViewPort 24x24
        private val pausePath =
            "M 8 5C6 5 6 5 6 7L6 17C6 18 6 19 8 19C9 19 10 18 10 17L10 7C10 5 9 5 8 5 Z M16 5C14 5 14 5 14 7L14 17C14 18 14 19 16 19C17 19 18 18 18 17L18 7C18 5 17 5 16 5z"

        //viewPort 108x108
        private val androidLogoData="M65.3,45.828l3.8,-6.6c0.2,-0.4 0.1,-0.9 -0.3,-1.1c-0.4,-0.2 -0.9,-0.1 -1.1,0.3l-3.9,6.7c-6.3,-2.8 -13.4,-2.8 -19.7,0l-3.9,-6.7c-0.2,-0.4 -0.7,-0.5 -1.1,-0.3C38.8,38.328 38.7,38.828 38.9,39.228l3.8,6.6C36.2,49.428 31.7,56.028 31,63.928h46C76.3,56.028 71.8,49.428 65.3,45.828zM43.4,57.328c-0.8,0 -1.5,-0.5 -1.8,-1.2c-0.3,-0.7 -0.1,-1.5 0.4,-2.1c0.5,-0.5 1.4,-0.7 2.1,-0.4c0.7,0.3 1.2,1 1.2,1.8C45.3,56.528 44.5,57.328 43.4,57.328L43.4,57.328zM64.6,57.328c-0.8,0 -1.5,-0.5 -1.8,-1.2s-0.1,-1.5 0.4,-2.1c0.5,-0.5 1.4,-0.7 2.1,-0.4c0.7,0.3 1.2,1 1.2,1.8C66.5,56.528 65.6,57.328 64.6,57.328L64.6,57.328z"


        object PathTypes {
            const val move = 'M'
            const val rMove = 'm'
            const val line = 'L'
            const val rLine = 'l'
            const val cubic = 'C'
            const val rCubic = 'c'
            const val hLine = 'H'
            const val rHLine = 'h'
            const val vLine = 'V'
            const val rVLine = 'v'
            const val stop = 'Z'
            const val rStop = 'z'//There hasn't any r. but available in upperCase
        }
    }
}

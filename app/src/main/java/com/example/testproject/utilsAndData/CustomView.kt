package com.example.testproject.utilsAndData

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Size
import android.view.View
import com.example.testproject.utilsAndData.data.VectorDrawConst
import com.example.testproject.utilsAndData.model.figmaModel.FigmaJson
import com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage.children.AbsoluteBoundingBox
import com.example.testproject.utilsAndData.model.figmaModel.document.figmaPage.children.Children

class CustomView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    /**Icon size multiply with this value so that icon scaled up to match size wit canvas*/
    //private var icScale = 10

    //Draw using this FigmaJson
    private var fJson: FigmaJson? = null
    private lateinit var absoluteBoundingBox: AbsoluteBoundingBox

    private val canvasSize = Size(400, 400)
    private val bg = ColorDrawable(Color.YELLOW)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 1f
        color = Color.RED
        //style = Paint.Style.STROKE
    }
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 2f
        color = Color.BLACK
    }
    private val textPaint = TextPaint().also {
        it.color = Color.RED
        it.strokeWidth = 20f
        it.textSize = 100f
    }
    //private val path = Path()

    init {
        background = bg
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //drawPathData(moneyPath, PointF(10f,10f),paint,canvas!!)//Draw here right side imminently
        fJson?.let { canvas?.let { drawFromJson(fJson!!, canvas) } }

        //myTestDraw(canvas!!)
    }

    private fun myTestDraw(canvas: Canvas) {
        val svgData = """
                M 10 290
                L 100 290

                A 20 100 45 1 1 150 290
                Z"
                """.trimIndent()
        val mPath = CanvasUtils.getPathFromPathData(svgData, PointF(0f, 0f))

        /*val mPath = Path().apply {
            moveTo(10f, 290f)
            lineTo(100f, 290f)

            val lastPos = PointF(100f, 290f)
            val svgPath = SVGPath(
                SVGPath.PathTypes.arc, listOf(20f, 100f, 45f, 1f, 1f, 150f, 290f)
            )
            svgPath.toPath(lastPos = lastPos, path = this)
            //this.addPath(svgPath.toPath(lastPos = lastPos))
        }*/

        pathDrawOnCanvas(canvas, mPath, strokePaint)

        //canvas.drawPath(path, strokePaint)
    }

    private fun drawFromJson(figmaJson: FigmaJson, canvas: Canvas) {
        //val json = GSonUtils.fromJson<FigmaJson>(FigmaJs.figmaSample)
        //figmaJson.get1stPage().children.forEach { findPathData(it, canvas) }

        val frame = figmaJson.get1stFrame()
        absoluteBoundingBox = frame.absoluteBoundingBox
        findPathData(frame, canvas)
    }

    private fun findPathData(children: Children, canvas: Canvas) {
        if (children.type == VectorDrawConst.ChildType.VECTOR) {

            logD("Details about vector=${children.name}")

            val fillGeometry = children.get1stGeometricPath()!!
            val fillColor = children.get1stSolidBgColor()
            val absPos = children.getTLMargin(absoluteBoundingBox)
            paint.color = fillColor ?: Color.BLACK
            drawPathData(fillGeometry, absPos, paint, canvas)


            val strokeGeometry = children.strokeGeometry
            // TODO: Please draw using these geometric data.

            logD("fillGeometry: $fillGeometry")
            logD("Color: $fillColor")
            logD("strokeGeometry: $strokeGeometry")
        } else {
            children.children.forEach { findPathData(it, canvas) }
        }
    }

    /**
     * @param pathData It's coded pathData. ex: 'M12.0 0 L15 2....'
     * @param absPos Left and Top margin/transition where draw this path.
     * @param color With which color to draw this path.
     * */
    private fun drawPathData(pathData: String, absPos: PointF, paint: Paint, canvas: Canvas) {
        val path = CanvasUtils.getPathFromPathData(pathData, absPos)
        pathDrawOnCanvas(canvas, path, paint)
    }

    private fun pathDrawOnCanvas(canvas: Canvas, path: Path, paint: Paint) {
        canvas.drawPath(path, paint)
    }

    fun setJsonInvalidate(figmaJson: FigmaJson) {
        fJson = figmaJson
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(canvasSize.width, canvasSize.height)
    }

    companion object {

        /**viewPort 150x150*/
        private const val starPath =
            "M68.5 35L78.2664 65.0578L109.871 65.0578L84.3023 83.6345L94.0687 113.692L68.5 95.1155L42.9313 113.692L52.6977 83.6345L27.129 65.0578L58.7336 65.0578L68.5 35Z"

        private const val clock = "" +
                "" +
                "M12,2" +
                "C6.477,2,2,6.477,2,12" +
                "c0,5.523,4.477,10,10,10" +
                "s10-4.477,10-10" +
                "C22,6.477,17.523,2,12,2" +
                "z " + "M15.293,16.70" +
                "7L11,12.414" + "V6h2 " +
                "v5.586" +
                "l3.707,3.707" +
                "L15.293,16.707" +
                "z"

        /**ViewPort 24x24*/
        private val pausePath = """
                M 8 5
                C6 5 6 5 6 7
                L6 17
                C6 18 6 19 8 19
                C9 19 10 18 10 17
                L10 7C10 5 9 5 8 5 
                Z 
                M16 5
                C14 5 14 5 14 7
                L14 17C14 18 14 19 16 19
                C17 19 18 18 18 17L18 7
                C18 5 17 5 16 5
                z
                """.trimIndent()

        /**viewPort 108x108*/
        private val androidLogoData = """
                M65.3,45.828
                l3.8,-6.6
                c0.2,-0.4 0.1,-0.9 -0.3,-1.1
                c-0.4,-0.2 -0.9,-0.1 -1.1,0.3
                l-3.9,6.7
                c-6.3,-2.8 -13.4,-2.8 -19.7,0
                l-3.9,-6.7
                c-0.2,-0.4 -0.7,-0.5 -1.1,-0.3
                C38.8,38.328 38.7,38.828 38.9,39.228
                l3.8,6.6
                C36.2,49.428 31.7,56.028 31,63.928
                h46
                C76.3,56.028 71.8,49.428 65.3,45.828
                z
                M43.4,57.328
                c-0.8,0 -1.5,-0.5 -1.8,-1.2
                c-0.3,-0.7 -0.1,-1.5 0.4,-2.1
                c0.5,-0.5 1.4,-0.7 2.1,-0.4
                c0.7,0.3 1.2,1 1.2,1.8
                C45.3,56.528 44.5,57.328 43.4,57.328
                L43.4,57.328z
                M64.6,57.328
                c-0.8,0 -1.5,-0.5 -1.8,-1.2
                s-0.1,-1.5 0.4,-2.1
                c0.5,-0.5 1.4,-0.7 2.1,-0.4
                c0.7,0.3 1.2,1 1.2,1.8
                C66.5,56.528 65.6,57.328 64.6,57.328
                L64.6,57.328
                z
                """.trimIndent()

        private const val loadingPath =
            "" + "M13 3 " + "A 1 1 0 0 0 12 4 " + "A 1 1 0 0 0 13 5 " + "A 1 1 0 0 0 14 4 " + "A 1 1 0 0 0 13 3 z M 8 5 A 1 1 0 0 0 7 6 " + "A 1 1 0 0 0 8 7 A 1 1 0 0 0 9 6 " + "A 1 1 0 0 0 8 5 " + "z " + "M 18 5 " + "A 1 1 0 0 0 17 6 " + "A 1 1 0 0 0 18 7 " + "A 1 1 0 0 0 19 6 " + "A 1 1 0 0 0 18 5 " + "" + "z " + "M 5.5 9 " + "A 2.5 2.5 0 0 0 3 11.5 " + "A 2.5 2.5 0 0 0 5.5 14 " + "A 2.5 2.5 0 0 0 8 11.5 " + "A 2.5 2.5 0 0 0 5.5 9 " + "z " + "M 19.5 10 " + "A 1.5 1.5 0 0 0 18 11.5 " + "A 1.5 1.5 0 0 0 19.5 13 " + "A 1.5 1.5 0 0 0 21 11.5 " + "A 1.5 1.5 0 0 0 19.5 10 " + "z " + "M 18.5 15 " + "A 1.5 1.5 0 0 0 17 16.5 " + "A 1.5 1.5 0 0 0 18.5 18 " + "A 1.5 1.5 0 0 0 20 16.5 " + "A 1.5 1.5 0 0 0 18.5 15 " + "z " + "M 8 16 " + "A 2 2 0 0 0 6 18 " + "A 2 2 0 0 0 8 20 " + "A 2 2 0 0 0 10 18 " + "A 2 2 0 0 0 8 16 " + "z " + "M 13.5 18 " + "A 1.5 1.5 0 0 0 12 19.5 " + "A 1.5 1.5 0 0 0 13.5 21 " + "A 1.5 1.5 0 0 0 15 19.5 " + "A 1.5 1.5 0 0 0 13.5 18 " + "z"

        /**ViewPort 90x90*/
        private const val acrNew =
            "" + "M0 45" + "C0 36.0999 2.6392 27.3996 7.58386 19.9994" + "C12.5285 12.5991 19.5566 6.83137 27.7792 3.42543" + "C36.0019 0.0194879 45.0499 -0.871649 53.7791 0.864686" + "C62.5082 2.60102 70.5264 6.88684 76.8198 13.1802" + "C83.1132 19.4736 87.399 27.4918 89.1353 36.2209" + "C90.8717 44.9501 89.9805 53.9981 86.5746 62.2208" + "C83.1686 70.4434 77.4009 77.4715 70.0007 82.4162" + "C62.6005 87.3608 53.9002 90 45 90" + "L45 45" + "L0 45" + "Z"

        /**ViewPort 70x62*/
        private const val moneyPath =
            "M0 0L0 1L0 42L0 45L0 46L0 50L1 50C29.9018 50 60.6367 61.9316 60.6367 61.9316L62 62.4629L62 52.9902C64.2687 53.5539 65.7168 53.959 65.7168 53.959L67 54.3379L67 42L70 42L70 0L0 0ZM2 2L68 2L68 40L2 40L2 2ZM13 4C12.7348 4 12.4804 4.10536 12.2929 4.29289C12.1054 4.48043 12 4.73478 12 5C12 5.26522 12.1054 5.51957 12.2929 5.70711C12.4804 5.89464 12.7348 6 13 6C13.2652 6 13.5196 5.89464 13.7071 5.70711C13.8946 5.51957 14 5.26522 14 5C14 4.73478 13.8946 4.48043 13.7071 4.29289C13.5196 4.10536 13.2652 4 13 4ZM17 4C16.7348 4 16.4804 4.10536 16.2929 4.29289C16.1054 4.48043 16 4.73478 16 5C16 5.26522 16.1054 5.51957 16.2929 5.70711C16.4804 5.89464 16.7348 6 17 6C17.2652 6 17.5196 5.89464 17.7071 5.70711C17.8946 5.51957 18 5.26522 18 5C18 4.73478 17.8946 4.48043 17.7071 4.29289C17.5196 4.10536 17.2652 4 17 4ZM21 4C20.7348 4 20.4804 4.10536 20.2929 4.29289C20.1054 4.48043 20 4.73478 20 5C20 5.26522 20.1054 5.51957 20.2929 5.70711C20.4804 5.89464 20.7348 6 21 6C21.2652 6 21.5196 5.89464 21.7071 5.70711C21.8946 5.51957 22 5.26522 22 5C22 4.73478 21.8946 4.48043 21.7071 4.29289C21.5196 4.10536 21.2652 4 21 4ZM25 4C24.7348 4 24.4804 4.10536 24.2929 4.29289C24.1054 4.48043 24 4.73478 24 5C24 5.26522 24.1054 5.51957 24.2929 5.70711C24.4804 5.89464 24.7348 6 25 6C25.2652 6 25.5196 5.89464 25.7071 5.70711C25.8946 5.51957 26 5.26522 26 5C26 4.73478 25.8946 4.48043 25.7071 4.29289C25.5196 4.10536 25.2652 4 25 4ZM29 4C28.7348 4 28.4804 4.10536 28.2929 4.29289C28.1054 4.48043 28 4.73478 28 5C28 5.26522 28.1054 5.51957 28.2929 5.70711C28.4804 5.89464 28.7348 6 29 6C29.2652 6 29.5196 5.89464 29.7071 5.70711C29.8946 5.51957 30 5.26522 30 5C30 4.73478 29.8946 4.48043 29.7071 4.29289C29.5196 4.10536 29.2652 4 29 4ZM33 4C32.7348 4 32.4804 4.10536 32.2929 4.29289C32.1054 4.48043 32 4.73478 32 5C32 5.26522 32.1054 5.51957 32.2929 5.70711C32.4804 5.89464 32.7348 6 33 6C33.2652 6 33.5196 5.89464 33.7071 5.70711C33.8946 5.51957 34 5.26522 34 5C34 4.73478 33.8946 4.48043 33.7071 4.29289C33.5196 4.10536 33.2652 4 33 4ZM37 4C36.7348 4 36.4804 4.10536 36.2929 4.29289C36.1054 4.48043 36 4.73478 36 5C36 5.26522 36.1054 5.51957 36.2929 5.70711C36.4804 5.89464 36.7348 6 37 6C37.2652 6 37.5196 5.89464 37.7071 5.70711C37.8946 5.51957 38 5.26522 38 5C38 4.73478 37.8946 4.48043 37.7071 4.29289C37.5196 4.10536 37.2652 4 37 4ZM41 4C40.7348 4 40.4804 4.10536 40.2929 4.29289C40.1054 4.48043 40 4.73478 40 5C40 5.26522 40.1054 5.51957 40.2929 5.70711C40.4804 5.89464 40.7348 6 41 6C41.2652 6 41.5196 5.89464 41.7071 5.70711C41.8946 5.51957 42 5.26522 42 5C42 4.73478 41.8946 4.48043 41.7071 4.29289C41.5196 4.10536 41.2652 4 41 4ZM45 4C44.7348 4 44.4804 4.10536 44.2929 4.29289C44.1054 4.48043 44 4.73478 44 5C44 5.26522 44.1054 5.51957 44.2929 5.70711C44.4804 5.89464 44.7348 6 45 6C45.2652 6 45.5196 5.89464 45.7071 5.70711C45.8946 5.51957 46 5.26522 46 5C46 4.73478 45.8946 4.48043 45.7071 4.29289C45.5196 4.10536 45.2652 4 45 4ZM49 4C48.7348 4 48.4804 4.10536 48.2929 4.29289C48.1054 4.48043 48 4.73478 48 5C48 5.26522 48.1054 5.51957 48.2929 5.70711C48.4804 5.89464 48.7348 6 49 6C49.2652 6 49.5196 5.89464 49.7071 5.70711C49.8946 5.51957 50 5.26522 50 5C50 4.73478 49.8946 4.48043 49.7071 4.29289C49.5196 4.10536 49.2652 4 49 4ZM53 4C52.7348 4 52.4804 4.10536 52.2929 4.29289C52.1054 4.48043 52 4.73478 52 5C52 5.26522 52.1054 5.51957 52.2929 5.70711C52.4804 5.89464 52.7348 6 53 6C53.2652 6 53.5196 5.89464 53.7071 5.70711C53.8946 5.51957 54 5.26522 54 5C54 4.73478 53.8946 4.48043 53.7071 4.29289C53.5196 4.10536 53.2652 4 53 4ZM57 4C56.7348 4 56.4804 4.10536 56.2929 4.29289C56.1054 4.48043 56 4.73478 56 5C56 5.26522 56.1054 5.51957 56.2929 5.70711C56.4804 5.89464 56.7348 6 57 6C57.2652 6 57.5196 5.89464 57.7071 5.70711C57.8946 5.51957 58 5.26522 58 5C58 4.73478 57.8946 4.48043 57.7071 4.29289C57.5196 4.10536 57.2652 4 57 4ZM58.0703 7.99805C57.8051 7.99805 57.5507 8.1034 57.3632 8.29094C57.1757 8.47848 57.0703 8.73283 57.0703 8.99805C57.0703 9.26326 57.1757 9.51762 57.3632 9.70515C57.5507 9.89269 57.8051 9.99805 58.0703 9.99805C58.3355 9.99805 58.5899 9.89269 58.7774 9.70515C58.965 9.51762 59.0703 9.26326 59.0703 8.99805C59.0703 8.73283 58.965 8.47848 58.7774 8.29094C58.5899 8.1034 58.3355 7.99805 58.0703 7.99805ZM11.9277 8.00195C11.6625 8.00195 11.4082 8.10731 11.2206 8.29485C11.0331 8.48238 10.9277 8.73674 10.9277 9.00195C10.9277 9.26717 11.0331 9.52152 11.2206 9.70906C11.4082 9.8966 11.6625 10.002 11.9277 10.002C12.193 10.002 12.4473 9.8966 12.6348 9.70906C12.8224 9.52152 12.9277 9.26717 12.9277 9.00195C12.9277 8.73674 12.8224 8.48238 12.6348 8.29485C12.4473 8.10731 12.193 8.00195 11.9277 8.00195ZM35 10C28.9367 10 24 14.9367 24 21C24 27.0633 28.9367 32 35 32C41.0633 32 46 27.0633 46 21C46 14.9367 41.0633 10 35 10ZM8.99805 10.9277C8.73283 10.9277 8.47848 11.0331 8.29094 11.2206C8.1034 11.4082 7.99805 11.6625 7.99805 11.9277C7.99805 12.193 8.1034 12.4473 8.29094 12.6348C8.47848 12.8224 8.73283 12.9277 8.99805 12.9277C9.26326 12.9277 9.51762 12.8224 9.70515 12.6348C9.89269 12.4473 9.99805 12.193 9.99805 11.9277C9.99805 11.6625 9.89269 11.4082 9.70515 11.2206C9.51762 11.0331 9.26326 10.9277 8.99805 10.9277ZM60.998 10.9297C60.7328 10.9297 60.4785 11.035 60.2909 11.2226C60.1034 11.4101 59.998 11.6645 59.998 11.9297C59.998 12.1949 60.1034 12.4493 60.2909 12.6368C60.4785 12.8243 60.7328 12.9297 60.998 12.9297C61.2633 12.9297 61.5176 12.8243 61.7052 12.6368C61.8927 12.4493 61.998 12.1949 61.998 11.9297C61.998 11.6645 61.8927 11.4101 61.7052 11.2226C61.5176 11.035 61.2633 10.9297 60.998 10.9297ZM5 12C4.73478 12 4.48043 12.1054 4.29289 12.2929C4.10536 12.4804 4 12.7348 4 13C4 13.2652 4.10536 13.5196 4.29289 13.7071C4.48043 13.8946 4.73478 14 5 14C5.26522 14 5.51957 13.8946 5.70711 13.7071C5.89464 13.5196 6 13.2652 6 13C6 12.7348 5.89464 12.4804 5.70711 12.2929C5.51957 12.1054 5.26522 12 5 12ZM35 12C39.9824 12 44 16.0176 44 21C44 25.9824 39.9824 30 35 30C30.0176 30 26 25.9824 26 21C26 16.0176 30.0176 12 35 12ZM65 12C64.7348 12 64.4804 12.1054 64.2929 12.2929C64.1054 12.4804 64 12.7348 64 13C64 13.2652 64.1054 13.5196 64.2929 13.7071C64.4804 13.8946 64.7348 14 65 14C65.2652 14 65.5196 13.8946 65.7071 13.7071C65.8946 13.5196 66 13.2652 66 13C66 12.7348 65.8946 12.4804 65.7071 12.2929C65.5196 12.1054 65.2652 12 65 12ZM5 16C4.73478 16 4.48043 16.1054 4.29289 16.2929C4.10536 16.4804 4 16.7348 4 17C4 17.2652 4.10536 17.5196 4.29289 17.7071C4.48043 17.8946 4.73478 18 5 18C5.26522 18 5.51957 17.8946 5.70711 17.7071C5.89464 17.5196 6 17.2652 6 17C6 16.7348 5.89464 16.4804 5.70711 16.2929C5.51957 16.1054 5.26522 16 5 16ZM65 16C64.7348 16 64.4804 16.1054 64.2929 16.2929C64.1054 16.4804 64 16.7348 64 17C64 17.2652 64.1054 17.5196 64.2929 17.7071C64.4804 17.8946 64.7348 18 65 18C65.2652 18 65.5196 17.8946 65.7071 17.7071C65.8946 17.5196 66 17.2652 66 17C66 16.7348 65.8946 16.4804 65.7071 16.2929C65.5196 16.1054 65.2652 16 65 16ZM14 17C11.8027 17 10 18.8027 10 21C10 23.1973 11.8027 25 14 25C16.1973 25 18 23.1973 18 21C18 18.8027 16.1973 17 14 17ZM55 17C52.8027 17 51 18.8027 51 21C51 23.1973 52.8027 25 55 25C57.1973 25 59 23.1973 59 21C59 18.8027 57.1973 17 55 17ZM14 19C15.1164 19 16 19.8836 16 21C16 22.1164 15.1164 23 14 23C12.8836 23 12 22.1164 12 21C12 19.8836 12.8836 19 14 19ZM55 19C56.1164 19 57 19.8836 57 21C57 22.1164 56.1164 23 55 23C53.8836 23 53 22.1164 53 21C53 19.8836 53.8836 19 55 19ZM5 20C4.73478 20 4.48043 20.1054 4.29289 20.2929C4.10536 20.4804 4 20.7348 4 21C4 21.2652 4.10536 21.5196 4.29289 21.7071C4.48043 21.8946 4.73478 22 5 22C5.26522 22 5.51957 21.8946 5.70711 21.7071C5.89464 21.5196 6 21.2652 6 21C6 20.7348 5.89464 20.4804 5.70711 20.2929C5.51957 20.1054 5.26522 20 5 20ZM65 20C64.7348 20 64.4804 20.1054 64.2929 20.2929C64.1054 20.4804 64 20.7348 64 21C64 21.2652 64.1054 21.5196 64.2929 21.7071C64.4804 21.8946 64.7348 22 65 22C65.2652 22 65.5196 21.8946 65.7071 21.7071C65.8946 21.5196 66 21.2652 66 21C66 20.7348 65.8946 20.4804 65.7071 20.2929C65.5196 20.1054 65.2652 20 65 20ZM5 24C4.73478 24 4.48043 24.1054 4.29289 24.2929C4.10536 24.4804 4 24.7348 4 25C4 25.2652 4.10536 25.5196 4.29289 25.7071C4.48043 25.8946 4.73478 26 5 26C5.26522 26 5.51957 25.8946 5.70711 25.7071C5.89464 25.5196 6 25.2652 6 25C6 24.7348 5.89464 24.4804 5.70711 24.2929C5.51957 24.1054 5.26522 24 5 24ZM65 24C64.7348 24 64.4804 24.1054 64.2929 24.2929C64.1054 24.4804 64 24.7348 64 25C64 25.2652 64.1054 25.5196 64.2929 25.7071C64.4804 25.8946 64.7348 26 65 26C65.2652 26 65.5196 25.8946 65.7071 25.7071C65.8946 25.5196 66 25.2652 66 25C66 24.7348 65.8946 24.4804 65.7071 24.2929C65.5196 24.1054 65.2652 24 65 24ZM5 28C4.73478 28 4.48043 28.1054 4.29289 28.2929C4.10536 28.4804 4 28.7348 4 29C4 29.2652 4.10536 29.5196 4.29289 29.7071C4.48043 29.8946 4.73478 30 5 30C5.26522 30 5.51957 29.8946 5.70711 29.7071C5.89464 29.5196 6 29.2652 6 29C6 28.7348 5.89464 28.4804 5.70711 28.2929C5.51957 28.1054 5.26522 28 5 28ZM65 28C64.7348 28 64.4804 28.1054 64.2929 28.2929C64.1054 28.4804 64 28.7348 64 29C64 29.2652 64.1054 29.5196 64.2929 29.7071C64.4804 29.8946 64.7348 30 65 30C65.2652 30 65.5196 29.8946 65.7071 29.7071C65.8946 29.5196 66 29.2652 66 29C66 28.7348 65.8946 28.4804 65.7071 28.2929C65.5196 28.1054 65.2652 28 65 28ZM60.998 29.0703C60.7328 29.0703 60.4785 29.1757 60.2909 29.3632C60.1034 29.5507 59.998 29.8051 59.998 30.0703C59.998 30.3355 60.1034 30.5899 60.2909 30.7774C60.4785 30.965 60.7328 31.0703 60.998 31.0703C61.2633 31.0703 61.5176 30.965 61.7052 30.7774C61.8927 30.5899 61.998 30.3355 61.998 30.0703C61.998 29.8051 61.8927 29.5507 61.7052 29.3632C61.5176 29.1757 61.2633 29.0703 60.998 29.0703ZM8.99805 29.0723C8.73283 29.0723 8.47848 29.1776 8.29094 29.3652C8.1034 29.5527 7.99805 29.807 7.99805 30.0723C7.99805 30.3375 8.1034 30.5918 8.29094 30.7794C8.47848 30.9669 8.73283 31.0723 8.99805 31.0723C9.26326 31.0723 9.51762 30.9669 9.70515 30.7794C9.89269 30.5918 9.99805 30.3375 9.99805 30.0723C9.99805 29.807 9.89269 29.5527 9.70515 29.3652C9.51762 29.1776 9.26326 29.0723 8.99805 29.0723ZM11.9258 31.998C11.6606 31.998 11.4062 32.1034 11.2187 32.2909C11.0311 32.4785 10.9258 32.7328 10.9258 32.998C10.9258 33.2633 11.0311 33.5176 11.2187 33.7052C11.4062 33.8927 11.6606 33.998 11.9258 33.998C12.191 33.998 12.4454 33.8927 12.6329 33.7052C12.8204 33.5176 12.9258 33.2633 12.9258 32.998C12.9258 32.7328 12.8204 32.4785 12.6329 32.2909C12.4454 32.1034 12.191 31.998 11.9258 31.998ZM58.0703 32.002C57.8051 32.002 57.5507 32.1073 57.3632 32.2948C57.1757 32.4824 57.0703 32.7367 57.0703 33.002C57.0703 33.2672 57.1757 33.5215 57.3632 33.7091C57.5507 33.8966 57.8051 34.002 58.0703 34.002C58.3355 34.002 58.5899 33.8966 58.7774 33.7091C58.965 33.5215 59.0703 33.2672 59.0703 33.002C59.0703 32.7367 58.965 32.4824 58.7774 32.2948C58.5899 32.1073 58.3355 32.002 58.0703 32.002ZM13 36C12.7348 36 12.4804 36.1054 12.2929 36.2929C12.1054 36.4804 12 36.7348 12 37C12 37.2652 12.1054 37.5196 12.2929 37.7071C12.4804 37.8946 12.7348 38 13 38C13.2652 38 13.5196 37.8946 13.7071 37.7071C13.8946 37.5196 14 37.2652 14 37C14 36.7348 13.8946 36.4804 13.7071 36.2929C13.5196 36.1054 13.2652 36 13 36ZM17 36C16.7348 36 16.4804 36.1054 16.2929 36.2929C16.1054 36.4804 16 36.7348 16 37C16 37.2652 16.1054 37.5196 16.2929 37.7071C16.4804 37.8946 16.7348 38 17 38C17.2652 38 17.5196 37.8946 17.7071 37.7071C17.8946 37.5196 18 37.2652 18 37C18 36.7348 17.8946 36.4804 17.7071 36.2929C17.5196 36.1054 17.2652 36 17 36ZM21 36C20.7348 36 20.4804 36.1054 20.2929 36.2929C20.1054 36.4804 20 36.7348 20 37C20 37.2652 20.1054 37.5196 20.2929 37.7071C20.4804 37.8946 20.7348 38 21 38C21.2652 38 21.5196 37.8946 21.7071 37.7071C21.8946 37.5196 22 37.2652 22 37C22 36.7348 21.8946 36.4804 21.7071 36.2929C21.5196 36.1054 21.2652 36 21 36ZM25 36C24.7348 36 24.4804 36.1054 24.2929 36.2929C24.1054 36.4804 24 36.7348 24 37C24 37.2652 24.1054 37.5196 24.2929 37.7071C24.4804 37.8946 24.7348 38 25 38C25.2652 38 25.5196 37.8946 25.7071 37.7071C25.8946 37.5196 26 37.2652 26 37C26 36.7348 25.8946 36.4804 25.7071 36.2929C25.5196 36.1054 25.2652 36 25 36ZM29 36C28.7348 36 28.4804 36.1054 28.2929 36.2929C28.1054 36.4804 28 36.7348 28 37C28 37.2652 28.1054 37.5196 28.2929 37.7071C28.4804 37.8946 28.7348 38 29 38C29.2652 38 29.5196 37.8946 29.7071 37.7071C29.8946 37.5196 30 37.2652 30 37C30 36.7348 29.8946 36.4804 29.7071 36.2929C29.5196 36.1054 29.2652 36 29 36ZM33 36C32.7348 36 32.4804 36.1054 32.2929 36.2929C32.1054 36.4804 32 36.7348 32 37C32 37.2652 32.1054 37.5196 32.2929 37.7071C32.4804 37.8946 32.7348 38 33 38C33.2652 38 33.5196 37.8946 33.7071 37.7071C33.8946 37.5196 34 37.2652 34 37C34 36.7348 33.8946 36.4804 33.7071 36.2929C33.5196 36.1054 33.2652 36 33 36ZM37 36C36.7348 36 36.4804 36.1054 36.2929 36.2929C36.1054 36.4804 36 36.7348 36 37C36 37.2652 36.1054 37.5196 36.2929 37.7071C36.4804 37.8946 36.7348 38 37 38C37.2652 38 37.5196 37.8946 37.7071 37.7071C37.8946 37.5196 38 37.2652 38 37C38 36.7348 37.8946 36.4804 37.7071 36.2929C37.5196 36.1054 37.2652 36 37 36ZM41 36C40.7348 36 40.4804 36.1054 40.2929 36.2929C40.1054 36.4804 40 36.7348 40 37C40 37.2652 40.1054 37.5196 40.2929 37.7071C40.4804 37.8946 40.7348 38 41 38C41.2652 38 41.5196 37.8946 41.7071 37.7071C41.8946 37.5196 42 37.2652 42 37C42 36.7348 41.8946 36.4804 41.7071 36.2929C41.5196 36.1054 41.2652 36 41 36ZM45 36C44.7348 36 44.4804 36.1054 44.2929 36.2929C44.1054 36.4804 44 36.7348 44 37C44 37.2652 44.1054 37.5196 44.2929 37.7071C44.4804 37.8946 44.7348 38 45 38C45.2652 38 45.5196 37.8946 45.7071 37.7071C45.8946 37.5196 46 37.2652 46 37C46 36.7348 45.8946 36.4804 45.7071 36.2929C45.5196 36.1054 45.2652 36 45 36ZM49 36C48.7348 36 48.4804 36.1054 48.2929 36.2929C48.1054 36.4804 48 36.7348 48 37C48 37.2652 48.1054 37.5196 48.2929 37.7071C48.4804 37.8946 48.7348 38 49 38C49.2652 38 49.5196 37.8946 49.7071 37.7071C49.8946 37.5196 50 37.2652 50 37C50 36.7348 49.8946 36.4804 49.7071 36.2929C49.5196 36.1054 49.2652 36 49 36ZM53 36C52.7348 36 52.4804 36.1054 52.2929 36.2929C52.1054 36.4804 52 36.7348 52 37C52 37.2652 52.1054 37.5196 52.2929 37.7071C52.4804 37.8946 52.7348 38 53 38C53.2652 38 53.5196 37.8946 53.7071 37.7071C53.8946 37.5196 54 37.2652 54 37C54 36.7348 53.8946 36.4804 53.7071 36.2929C53.5196 36.1054 53.2652 36 53 36ZM57 36C56.7348 36 56.4804 36.1054 56.2929 36.2929C56.1054 36.4804 56 36.7348 56 37C56 37.2652 56.1054 37.5196 56.2929 37.7071C56.4804 37.8946 56.7348 38 57 38C57.2652 38 57.5196 37.8946 57.7071 37.7071C57.8946 37.5196 58 37.2652 58 37C58 36.7348 57.8946 36.4804 57.7071 36.2929C57.5196 36.1054 57.2652 36 57 36ZM2 42L59 42L65 42L65 51.709C61.7349 50.7906 36.9959 44.1596 2 44.0254L2 42ZM59 42C58.7348 42 58.4804 42.1054 58.2929 42.2929C58.1054 42.4804 58 42.7348 58 43C58 43.2652 58.1054 43.5196 58.2929 43.7071C58.4804 43.8946 58.7348 44 59 44C59.2652 44 59.5196 43.8946 59.7071 43.7071C59.8946 43.5196 60 43.2652 60 43C60 42.7348 59.8946 42.4804 59.7071 42.2929C59.5196 42.1054 59.2652 42 59 42ZM57 45C56.7348 45 56.4804 45.1054 56.2929 45.2929C56.1054 45.4804 56 45.7348 56 46C56 46.2652 56.1054 46.5196 56.2929 46.7071C56.4804 46.8946 56.7348 47 57 47C57.2652 47 57.5196 46.8946 57.7071 46.7071C57.8946 46.5196 58 46.2652 58 46C58 45.7348 57.8946 45.4804 57.7071 45.2929C57.5196 45.1054 57.2652 45 57 45ZM2 46.0137C23.71 46.0957 41.7222 48.7735 53 51.0039C53.001 51.2684 53.1068 51.5218 53.2943 51.7085C53.4817 51.8952 53.7355 52 54 52C54.197 51.9999 54.3896 51.9417 54.5536 51.8326C54.7176 51.7235 54.8457 51.5684 54.9219 51.3867C56.9134 51.7983 58.5316 52.1674 60 52.5117L60 59.582C56.4431 58.2375 29.2467 48.2964 2 48.0469L2 46.0137ZM52 53C51.7348 53 51.4804 53.1054 51.2929 53.2929C51.1054 53.4804 51 53.7348 51 54C51 54.2652 51.1054 54.5196 51.2929 54.7071C51.4804 54.8946 51.7348 55 52 55C52.2652 55 52.5196 54.8946 52.7071 54.7071C52.8946 54.5196 53 54.2652 53 54C53 53.7348 52.8946 53.4804 52.7071 53.2929C52.5196 53.1054 52.2652 53 52 53Z"

        //ViewPort 24x24
        private val closeIconData = """
            M120 100

            L 70 50
            A 10 10 0 0 0 50 70
            L 100 120
        
            L 50 170
            A 10 10 0 1 0 70 190
            L 120 140
        
            L 170 190
            A 10 10 0 1 0 190 170
            L 140 120
        
            L 190 70
            A 10 10 0 0 0 170 50
            L 120 100
            
            z
            """.trimIndent()


        private val instaAbsPos = listOf(
            PointF(0f, 0f),//bg
            PointF(11.086f, 11.086f),//outer circle
            PointF(25.869f, 25.869f),//inner circle
            PointF(55.435f, 22.174f),//Dot
        )

        /**ViewPort 85x85*/
        private val instaPath = listOf(
            //BG gradient
            "M66.5217 85L18.4783 85C8.31522 85 0 76.6848 0 66.5217L0 18.4783C0 8.31522 8.31522 0 18.4783 0L66.5217 0C76.6848 0 85 8.31522 85 18.4783L85 66.5217C85 76.6848 76.6848 85 66.5217 85Z",
            //Outline circle
            "" + "M31.413 5.54348" + "C39.913 5.54348 40.837 5.54348 44.163 5.72826" + "C47.3044 5.91304 48.9674 6.46739 50.0761 6.83696" + "C51.5543 7.39131 52.663 8.13043 53.7717 9.23913" + "C54.8804 10.3478 55.6196 11.4565 56.1739 12.9348" + "C56.5435 14.0435 57.0978 15.7065 57.2826 18.8478" + "C57.2826 21.9891 57.2826 22.913 57.2826 31.413" + "C57.2826 39.913 57.2826 40.837 57.0978 44.163" + "C56.913 47.3044 56.3587 48.9674 55.9891 50.0761" + "C55.4348 51.5543 54.6957 52.663 53.587 53.7717" + "C52.4783 54.8804 51.3696 55.6196 49.8913 56.1739" + "C48.7826 56.5435 47.1196 57.0978 43.9783 57.2826" + "C40.837 57.2826 39.913 57.2826 31.413 57.2826" + "C22.913 57.2826 21.9891 57.2826 18.663 57.0978" + "C15.5217 56.913 13.8587 56.3587 12.75 55.9891" + "C11.2717 55.4348 10.163 54.6957 9.05435 53.587" + "C7.94565 52.4783 7.20652 51.3696 6.65217 49.8913" + "C6.28261 48.7826 5.72826 47.1196 5.54348 43.9783" + "C5.54348 40.837 5.54348 39.913 5.54348 31.413" + "C5.54348 22.913 5.54348 21.9891 5.72826 18.663" + "C5.91304 15.5217 6.46739 13.8587 6.83696 12.75" + "C7.39131 11.2717 8.13043 10.163 9.23913 9.05435" + "C10.3478 7.94565 11.4565 7.20652 12.9348 6.65217" + "C14.0435 6.28261 15.7065 5.72826 18.8478 5.54348" + "C21.9891 5.54348 22.913 5.54348 31.413 5.54348ZM31.413 0" + "C22.913 0 21.8043 7.02136e-07 18.4783 0.184783" + "C15.1522 0.369566 12.9348 0.923912 10.9022 1.66304" + "C8.86957 2.40217 7.02174 3.51087 5.3587 5.3587" + "C3.69565 7.20652 2.58696 8.86957 1.66304 10.9022" + "C0.923912 12.9348 0.369566 15.1522 0.184783 18.4783" + "C7.02136e-07 21.8043 0 22.913 0 31.413" + "C0 39.913 7.02136e-07 41.0217 0.184783 44.3478" + "C0.369566 47.6739 0.923912 49.8913 1.66304 51.9239" + "C2.40217 53.9565 3.51087 55.8043 5.3587 57.4674" + "C7.02174 59.1304 8.86957 60.2391 10.9022 61.163" + "C12.9348 61.9022 15.1522 62.4565 18.4783 62.6413" + "C21.8043 62.8261 22.913 62.8261 31.413 62.8261" + "C39.913 62.8261 41.0217 62.8261 44.3478 62.6413" + "C47.6739 62.4565 49.8913 61.9022 51.9239 61.163" + "C53.9565 60.4239 55.8043 59.3152 57.4674 57.4674" + "C59.1304 55.8043 60.2391 53.9565 61.163 51.9239" + "C61.9022 49.8913 62.4565 47.6739 62.6413 44.3478" + "C62.8261 41.0217 62.8261 39.913 62.8261 31.413" + "C62.8261 22.913 62.8261 21.8043 62.6413 18.4783" + "C62.4565 15.1522 61.9022 12.9348 61.163 10.9022" + "C60.4239 8.86957 59.3152 7.02174 57.4674 5.3587" + "C55.6196 3.69565 53.9565 2.58696 51.9239 1.66304" + "C49.8913 0.923912 47.6739 0.369566 44.3478 0.184783" + "C41.0217 7.02136e-07 39.913 0 31.413 0" + "Z",
            //Inner circle
            "M16.6304 0C7.3913 0 0 7.3913 0 16.6304C0 25.8696 7.3913 33.2609 16.6304 33.2609C25.8696 33.2609 33.2609 25.8696 33.2609 16.6304C33.2609 7.3913 25.8696 0 16.6304 0ZM16.6304 27.7174C10.5326 27.7174 5.54348 22.7283 5.54348 16.6304C5.54348 10.5326 10.5326 5.54348 16.6304 5.54348C22.7283 5.54348 27.7174 10.5326 27.7174 16.6304C27.7174 22.7283 22.7283 27.7174 16.6304 27.7174Z",
            //Dot
            "M3.69565 7.3913C5.7367 7.3913 7.3913 5.7367 7.3913 3.69565C7.3913 1.6546 5.7367 0 3.69565 0C1.6546 0 0 1.6546 0 3.69565C0 5.7367 1.6546 7.3913 3.69565 7.3913Z",
        )


        //Ref: https://www.w3schools.com/graphics/svg_path.asp
        //Explanation: https://developer.mozilla.org/en-US/docs/Web/SVG/Tutorial/Paths
    }
}

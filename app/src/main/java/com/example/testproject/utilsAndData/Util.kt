package com.example.testproject.utilsAndData

import android.util.Log
import androidx.annotation.FloatRange
import com.example.testproject.utilsAndData.model.figmaModel.SizeX
import com.learner.codereducer.utils.GSonUtils

fun logD(msg: String) = Log.d("xyz", msg)

fun toColorInt(
    @FloatRange(from = 0.0, to = 1.0) alpha: Double,
    @FloatRange(from = 0.0, to = 1.0) red: Double,
    @FloatRange(from = 0.0, to = 1.0) green: Double,
    @FloatRange(from = 0.0, to = 1.0) blue: Double,
): Int {
    return (((alpha * 255 + .5).toInt() shl 24) or
            ((red * 255 + .5).toInt() shl 16) or
            ((green * 255 + .5).toInt() shl 8) or
            ((blue * 255 + .5).toInt()))
}

fun Float.toBoolean() = (this > 0)


fun main() {
    val x = "a@bc.d...123.@%^%^$&".replace(
        "[^a-zA-Z0-9.]+".toRegex(),
        "r"
    )

    val json = """
        {
            "size":{
                "x": 320,
                "y": 240
            }
        }
    """.trimIndent()
    val sz = GSonUtils.fromJson<mSize>(json)
    println(sz)
}

data class mSize(
    val size: SizeX
)
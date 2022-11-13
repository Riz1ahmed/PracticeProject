package com.example.testproject.utilsAndData

import android.util.Log

fun logD(msg: String) = Log.d("xyz", msg)

fun main(args: Array<String>) {
    val x = "a@bc.d...123.@%^%^$&".replace(
        "[^a-zA-Z0-9.]+".toRegex(),
        "r"
    )
    println(x)

    "7.02136e-07"
}
package ru.guar7387.kotlinfunny.basics

import android.util.Log

public val isDebug: Boolean = true

public fun log(tag: String, message: String) {
    Log.i(tag, message)
}


public fun testingLog() {
    Logger.log("AA", "bb")
}



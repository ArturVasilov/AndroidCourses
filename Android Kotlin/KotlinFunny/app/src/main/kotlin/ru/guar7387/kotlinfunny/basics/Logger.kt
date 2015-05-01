package ru.guar7387.kotlinfunny.basics

import android.util.Log
import ru.guar7387.kotlinfunny

public class Logger {

    private val isDebug = true;

    class object {
        fun log(tag: String, message: String) {
            if (isDebug) {
                Log.i(tag, message)
            }
        }
    }

}




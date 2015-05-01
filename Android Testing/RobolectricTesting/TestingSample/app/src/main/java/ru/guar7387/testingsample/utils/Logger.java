package ru.guar7387.testingsample.utils;

import android.util.Log;

public class Logger {

    private static final boolean isDebug = true;

    public static void i(String tag, String message) {
        if (isDebug) {
            Log.i(tag, message);
        }
    }

    /*
        other level methods
     */
}



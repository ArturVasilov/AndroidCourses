package ru.guar7387.clientserverwithvolleysample.utils;

import android.util.Log;

public class Logger {

    private Logger() { }

    private static final boolean isDebug = true;

    public static void log(String tag, String message) {
        if (isDebug) {
            Log.i(tag, message);
        }
    }

}

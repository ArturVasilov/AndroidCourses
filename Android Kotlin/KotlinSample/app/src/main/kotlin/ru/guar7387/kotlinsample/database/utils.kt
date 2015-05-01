package ru.guar7387.kotlinsample.database

import ru.guar7387.kotlinsample.data.Earthquake
import android.content.ContentValues

public fun Earthquake.contentValues() : ContentValues {
    val args = ContentValues()
    args.put(DATE_TIME, dateTime)
    args.put(MAGNITUDE, magnitude)
    args.put(LOCATION, location)


    val func: (x: Int) -> Int = { it * 2 }

    return args
}






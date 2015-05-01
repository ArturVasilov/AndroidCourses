package ru.guar7387.kotlinsample.database

import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.database.sqlite.SQLiteDatabase

public class EarthquakeDatabase(val context: Context) :
        SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            DATE_TIME + " INTEGER, " +
            MAGNITUDE + " INTEGER, " +
            LOCATION + " TEXT, " +
            "PRIMARY KEY (" + DATE_TIME + ", " + MAGNITUDE + ", " + LOCATION + "));")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)

        onCreate(db)
    }

}



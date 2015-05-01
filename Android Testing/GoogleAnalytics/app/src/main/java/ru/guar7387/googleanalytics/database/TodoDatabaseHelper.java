package ru.guar7387.googleanalytics.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class TodoDatabaseHelper extends SQLiteOpenHelper implements BaseColumns, DatabaseConstants {

    private static final String DATABASE_NAME = "ru.guar7387.npp.samples.todos";

    private static final int DATABASE_VERSION = 1;

    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TODO_TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TODO_TEXT + " TEXT NON NULL, " +
                TODO_TIME + " LONG NON NULL" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE_NAME);
        onCreate(db);
    }
}

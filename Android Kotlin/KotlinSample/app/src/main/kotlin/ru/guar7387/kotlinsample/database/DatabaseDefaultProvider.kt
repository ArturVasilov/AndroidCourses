package ru.guar7387.kotlinsample.database

import ru.guar7387.kotlinsample.data.Earthquake
import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import java.util.ArrayList

public class DatabaseDefaultProvider(val context: Context) : DatabaseProvider {

    val database = EarthquakeDatabase(context).getWritableDatabase()

    override fun saveEarthquakes(earthquakes: List<Earthquake>, filter: (Earthquake) -> Boolean) {
        for (earthquake in earthquakes) {
            if (filter.invoke(earthquake)) {
                database.insertWithOnConflict(TABLE_NAME, null,
                        earthquake.contentValues(), SQLiteDatabase.CONFLICT_REPLACE)
            }
        }
    }

    override fun readEarthquakes(filter: (Earthquake) -> Boolean): List<Earthquake> {
        val all = database.rawQuery(TABLE_NAME, array(DATE_TIME, MAGNITUDE, LOCATION))
        val result = ArrayList<Earthquake>()
        while (all.moveToNext()) {
            val earthquake = Earthquake(all.getLong(0), all.getInt(1), all.getString(2))
            if (filter.invoke(earthquake))
                result.add(earthquake)
        }
        return result
    }

    override fun close() {
        database.close()
    }
}





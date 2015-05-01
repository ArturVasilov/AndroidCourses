package ru.guar7387.kotlinsample

import ru.guar7387.kotlinsample.data.downloadEarthquakes
import android.app.IntentService
import android.content.Intent
import ru.guar7387.kotlinsample.data.EARTHQUAKES_KEY
import org.json.JSONObject

public class EarthquakeService : IntentService("EarthquakeService") {

    override fun onHandleIntent(intent: Intent?) {
        log("EarthquakeService", "onHandleIntent")
        val earthquakes = downloadEarthquakes(request)
        val action = getResources().getString(R.string.earthquakes_downloaded_action)
        val earthquakesIntent = Intent(action)
        earthquakesIntent.putParcelableArrayListExtra(EARTHQUAKES_KEY, earthquakes)
        sendBroadcast(earthquakesIntent)
    }

    val request : String
        get() = "http://earthquake.usgs.gov/fdsnws/event/1/query?" +
                "format=geojson&starttime=2014-01-01&endtime=2014-01-30&minmagnitude=4"

}




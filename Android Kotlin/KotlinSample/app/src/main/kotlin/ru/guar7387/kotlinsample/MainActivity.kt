package ru.guar7387.kotlinsample

import android.support.v7.app.ActionBarActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.guar7387.kotlinsample.data.Earthquake
import java.util.ArrayList
import android.content.IntentFilter
import ru.guar7387.kotlinsample.data.EARTHQUAKES_KEY
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.os.Parcelable
import ru.guar7387.kotlinsample.database.DatabaseDefaultProvider

public class MainActivity : ActionBarActivity() {

    private val TAG = "MainActivity"

    private val earthquakeReceiver = EarthquakesReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        log(TAG, "onCreate")

        val filter = IntentFilter(getString(R.string.earthquakes_downloaded_action))
        registerReceiver(earthquakeReceiver, filter)

        log(TAG, "Receivers registered")

        val intent = Intent(getApplicationContext(), javaClass<EarthquakeService>())
        startService(intent)
        log(TAG, "service started")
    }

    override fun onStart() {
        super.onStart()

        val filter = IntentFilter(getString(R.string.earthquakes_downloaded_action))
        registerReceiver(earthquakeReceiver, filter)

        log(TAG, "Receivers registered")
    }

    override fun onStop() {
        super.onStop()
        try {
            unregisterReceiver(earthquakeReceiver)
        } catch (ignored: IllegalStateException) {
        }
    }

    private fun updateRequests(earthquakes: ArrayList<Earthquake>) {
        val database = DatabaseDefaultProvider(getApplicationContext())
        database.saveEarthquakes(earthquakes, {it.magnitude >= 5})

        val fragment = EarthquakesFragment()
        val args = Bundle()
        args.putParcelableArrayList(EARTHQUAKES_KEY, earthquakes)
        fragment.setArguments(args)

        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit()
    }

    public inner class EarthquakesReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            log("EarthquakeReceiver", "onReceive")
            if (intent.getAction().equalsIgnoreCase(getResources().getString(
                    R.string.earthquakes_downloaded_action))) {
                updateRequests(intent.getParcelableArrayListExtra<Earthquake>(EARTHQUAKES_KEY))
            }
        }

    }
}






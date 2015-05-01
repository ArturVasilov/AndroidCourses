package ru.guar7387.rxjavasample

import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import android.widget.TextView
import ru.guar7387.kotlinsample.data.downloadEarthquakes
import ru.guar73873.rxjavasample.ReactiveEarthquakes
import rx.Observable
import rx.Subscriber
import rx.schedulers.Schedulers

public class MainActivity : ActionBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dateStart = "2015-04-08"
        val dateEnd = "2015-04-10"
        val magnitude = 4
        Observable.just(
                {
                    downloadEarthquakes("http://earthquake.usgs.gov/fdsnws/event/1/query?" +
                            "format=geojson&starttime=" + dateStart + "&endtime=" +
                            dateEnd + "&minmagnitude=" + magnitude);
                })
        .observeOn(Schedulers.newThread())
        .subscribe({ ReactiveEarthquakes(it.invoke()).run() })
    }
}


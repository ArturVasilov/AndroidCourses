package ru.guar73873.rxjavasample

import android.util.Log
import android.widget.TextView
import ru.guar7387.rxjavasample.Earthquake
import rx.Observable
import rx.Subscriber

public class ReactiveEarthquakes(val earthquakes: List<Earthquake>) {

    public fun run() {
        val observer = Observable.from(earthquakes)
        observer
            .filter( { it.magnitude > 4 })
            .map( { it -> Earthquake(it.dateTime + 1000000, it.magnitude, it.location)})
            .subscribe( { Log.i("ReactiveEarthquakes", it.toString()) })
    }

}



package ru.guar7387.kotlinsample.database

import ru.guar7387.kotlinsample.data.Earthquake

public trait DatabaseProvider {

    public fun saveEarthquakes(earthquakes: List<Earthquake>,
                               filter: (val earthquake: Earthquake) -> Boolean = {true})

    public fun readEarthquakes(filter: (val earthquake: Earthquake) -> Boolean = {true})
            : List<Earthquake>

    public fun close()
}



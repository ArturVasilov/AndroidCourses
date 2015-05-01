package ru.guar7387.kotlinsample.data

import org.json.JSONObject
import ru.guar7387.rxjavasample.Earthquake
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList

val TAG = "downloading earthquakes"

val CONNECTION_TIMEOUT = 15000;
val READ_TIMEOUT = 10000;

public fun downloadEarthquakes(request: String) :
        List<Earthquake> {
    val inputStream : InputStream
    try {
        val url = URL(request)
        val connection = url.openConnection() as HttpURLConnection
        connection.setReadTimeout(READ_TIMEOUT)
        connection.setConnectTimeout(CONNECTION_TIMEOUT)
        connection.setRequestMethod("POST")
        connection.setDoInput(true)
        connection.setDoOutput(true)

        connection.connect()

        inputStream = connection.getInputStream()
    } catch (e: IOException) {
        return ArrayList()
    }

    try {
        val reader = BufferedReader(InputStreamReader(inputStream, "utf-8"), 8)
        val sb = StringBuilder()
        var line = reader.readLine()
        while (line != null) {
            sb.append(line).append("\n")
            line = reader.readLine()
        }
        inputStream.close()
        val json = JSONObject(sb.toString())
        return parseJson(json)
    } catch (e: Exception) {
        return ArrayList()
    }
}

fun parseJson(json: JSONObject): ArrayList<Earthquake> {
    val result = ArrayList<Earthquake>()
    val features = json.getJSONArray("features")
    for (index in 0..features.length() - 1) {
        val props = (features[index] as JSONObject).getJSONObject("properties")
        result.add(Earthquake(props.getLong("time"), props.getInt("mag"), props.getString("place")))
    }
    return result
}




package ru.guar7387.kotlinsample.data

import ru.guar7387.kotlinsample.EarthquakeService
import java.util.ArrayList
import java.net.URL
import org.apache.http.message.BasicNameValuePair
import org.json.JSONObject
import ru.guar7387.kotlinsample.log
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.io.IOException
import java.io.InputStreamReader
import java.io.BufferedReader
import java.net.HttpURLConnection

val TAG = "downloading earthquakes"

val CONNECTION_TIMEOUT = 15000;
val READ_TIMEOUT = 10000;

public fun EarthquakeService.downloadEarthquakes(request: String) :
        ArrayList<Earthquake> {
    log(TAG, "Request. Params - " + request)
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
        log(TAG, "IOException during sending request")
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
        log(TAG, "answer - " + sb)
        val json = JSONObject(sb.toString())
        return parseJson(json)
    } catch (e: Exception) {
        log(TAG, "Buffer error, converting result " + e.toString())
        return ArrayList()
    }
}

fun parseJson(json: JSONObject): ArrayList<Earthquake> {
    log(TAG, "Result json - " + json)
    val result = ArrayList<Earthquake>()
    val features = json.getJSONArray("features")
    for (index in 0..features.length() - 1) {
        val props = (features[index] as JSONObject).getJSONObject("properties")
        result.add(Earthquake(props.getLong("time"), props.getInt("mag"), props.getString("place")))
    }
    log(TAG, features.toString())
    return result
}




package ru.guar7387.servermodule.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.guar7387.servermodule.Logger;

public class StandardServerConnection implements ServerConnection {

    private static final String TAG = StandardServerConnection.class.getSimpleName();

    private static final int CONNECTION_TIMEOUT = 15000;
    private static final int READ_TIMEOUT = 10000;

    private final String url;

    private HttpURLConnection mConnection;

    public StandardServerConnection(String url) {
        this.url = url;
    }

    @Override
    public void prepareAndConnect(ConnectionRequest request) throws IOException {
        URL url = new URL(this.url);
        mConnection = (HttpURLConnection) url.openConnection();
        mConnection.setReadTimeout(READ_TIMEOUT);
        mConnection.setConnectTimeout(CONNECTION_TIMEOUT);
        mConnection.setRequestMethod("POST");
        mConnection.setDoInput(true);
        mConnection.setDoOutput(true);

        OutputStream outputStream = null;
        try {
            outputStream = mConnection.getOutputStream();
            Logger.log(TAG, "Sending request: " + request.createRequest());
            String charset = "UTF-8";
            outputStream.write(request.createRequest().getBytes(charset));
        } catch (Exception e) {
            Logger.log(TAG, "Exception during writing request");
            outputStream = null;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
        mConnection.connect();
    }

    @Override
    public JSONObject readOutput() throws IOException {
        int responseCode = mConnection.getResponseCode();
        String responseMessage = mConnection.getResponseMessage();

        Logger.log(TAG, "Creating connection; response code - " + responseCode
                + "; responseMessage - " + responseMessage);
        String result = null;

        InputStream inputStream = mConnection.getInputStream();
        String charset = "UTF-8";
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset), 32);
        //noinspection TryFinallyCanBeTryWithResources
        try {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = reader.readLine();
            }
            inputStream.close();
            result = sb.toString();
            Logger.log(TAG, "Json string - " + result);
        } catch (Exception e) {
            Logger.log(TAG, "Buffer error, converting result " + e.toString());
        } finally {
            reader.close();
        }

        mConnection.disconnect();
        try {
            return new JSONObject(result);
        } catch (JSONException e) {
            Logger.log(TAG, "Error parsing data " + e.toString());
            return null;
        }
    }
}

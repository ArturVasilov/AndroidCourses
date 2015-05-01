package ru.guar7387.servermodule.api;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ru.guar7387.servermodule.Logger;

@SuppressWarnings("deprecation")
public class DeprecatedServerConnection implements ServerConnection {

    private static final int CONNECTION_TIMEOUT = 15000;

    private final String url;

    private HttpResponse httpResponse;

    public DeprecatedServerConnection(String url) {
        this.url = url;
    }

    @Override
    public void prepareAndConnect(ConnectionRequest request) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        Logger.log("Deprecated server connection", "main method");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(RequestFactory.ALL_ARTICLES, "{\"token\":\"" + RequestFactory.API_KEY + "\"}"));

        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
        httpClient.setParams(httpParams);

        httpResponse = httpClient.execute(httpPost);
    }

    @Override
    public JSONObject readOutput() throws IOException {
        HttpEntity httpEntity = httpResponse.getEntity();
        InputStream inputStream = httpEntity.getContent();

        String jsonString = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = reader.readLine();
            }
            inputStream.close();
            jsonString = sb.toString();
        } catch (Exception ignored) {
        }

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            jsonObject = new JSONObject();
        }

        return jsonObject;
    }
}



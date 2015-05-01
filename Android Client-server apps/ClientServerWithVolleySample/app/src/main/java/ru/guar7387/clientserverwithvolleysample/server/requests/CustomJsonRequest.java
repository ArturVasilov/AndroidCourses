package ru.guar7387.clientserverwithvolleysample.server.requests;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.Map;

public interface CustomJsonRequest {

    int getMethod();

    String getUrl();

    Map<String, String> getParams();

    Response.Listener<JSONObject> getSuccessListener();

    Response.ErrorListener getErrorListener();

}



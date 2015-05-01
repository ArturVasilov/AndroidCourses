package ru.guar73873.volleybasicssample;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://androidtutorials.ru/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        TextView stringRequestTextView = (TextView) findViewById(R.id.stringRequestTextView);
                        stringRequestTextView.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://androidtutorials.ru/api/?ApiBlog.allArticles={\"token\":\"kktb56yjdfg3691ubmpd0qhd\"}",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        TextView jsonRequestTextView = (TextView) findViewById(R.id.jsonRequestTextView);
                        jsonRequestTextView.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        ImageRequest imageRequest = new ImageRequest(
                "http://blog.zitec.com/wp-content/uploads/2014/02/android-volley-library.jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ImageView imageView = (ImageView) findViewById(R.id.imageRequestBitmap);
                        imageView.setImageBitmap(response);
                    }
                }, 500, 500, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        String dateStart = "2015-04-08";
        String dateEnd = "2015-04-10";
        int magnitude = 4;
        String methodBody = "format=geojson&starttime=" + dateStart +
                "&endtime=" + dateEnd + "&minmagnitude=" + magnitude;
        JsonObjectRequest earthquakesRequest = new JsonObjectRequest(Request.Method.GET,
                "http://earthquake.usgs.gov/fdsnws/event/1/query",
                methodBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        TextView earthquakeRequestTextView = (TextView) findViewById(R.id.earthquakesRequest);
                        earthquakeRequestTextView.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("MainActivity", "error; " + error.getMessage());
                    }
                });

        stringRequest.setTag(TAG);
        jsonObjectRequest.setTag(TAG);
        imageRequest.setTag(TAG);
        earthquakesRequest.setTag(TAG);

        mRequestQueue.add(stringRequest);
        mRequestQueue.add(jsonObjectRequest);
        mRequestQueue.add(imageRequest);
        mRequestQueue.add(earthquakesRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRequestQueue.cancelAll(TAG);
    }
}

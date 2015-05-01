package ru.guar7387.clientserverwithvolleysample.server.requests;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class CustomRequest extends Request<JSONObject> {

    private final Response.Listener<JSONObject> listener;
    private final Map<String, String> params;

    public CustomRequest(CustomJsonRequest request) {
        super(request.getMethod(), request.getUrl(), request.getErrorListener());
        this.listener = request.getSuccessListener();
        this.params = request.getParams();
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
        return params;
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }
}



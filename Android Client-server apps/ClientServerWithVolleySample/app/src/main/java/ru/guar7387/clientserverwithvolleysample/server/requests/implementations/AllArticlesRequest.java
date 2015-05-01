package ru.guar7387.clientserverwithvolleysample.server.requests.implementations;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.guar7387.clientserverwithvolleysample.data.Article;
import ru.guar7387.clientserverwithvolleysample.server.callbacks.AllArticlesLoadedCallback;
import ru.guar7387.clientserverwithvolleysample.server.requests.AndroidTutorialsJsonRequest;
import ru.guar7387.clientserverwithvolleysample.utils.Logger;

public class AllArticlesRequest extends AndroidTutorialsJsonRequest {

    private static final String TAG = AllArticlesRequest.class.getSimpleName();

    private final AllArticlesLoadedCallback callback;

    public AllArticlesRequest(AllArticlesLoadedCallback callback) {
        this.callback = callback;
    }

    @Override
    public Map<String, String> getParams() {
        return new HashMap<String, String>() {{
            put(ALL_ARTICLES, "{\"token\":\"" + API_KEY + "\"}");
        }};
    }

    @Override
    public Response.Listener<JSONObject> getSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Logger.log(TAG, "Response");
                    JSONObject answer = response.getJSONObject("response");
                    if (!answer.getString("answer").equalsIgnoreCase("ok")) {
                        return;
                    }
                    JSONArray articles = answer.getJSONArray("result");
                    List<Article> articlesList = new ArrayList<>();
                    for (int i = 0; i < articles.length(); i++) {
                        JSONArray array = articles.getJSONArray(i);
                        int id = array.getInt(0);
                        String title = array.getString(1);
                        String description = array.getString(2);
                        String url = array.getString(3);
                        String date = array.getString(4);
                        Article article = new Article(id, title, description, url, date);
                        articlesList.add(article);
                    }
                    callback.onAllArticlesLoaded(articlesList);
                } catch (JSONException ignored) {
                }
            }
        };
    }
}



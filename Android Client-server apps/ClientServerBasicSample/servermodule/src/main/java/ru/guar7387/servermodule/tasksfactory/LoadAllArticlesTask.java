package ru.guar7387.servermodule.tasksfactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import ru.guar7387.servermodule.callbacks.Callback;
import ru.guar7387.servermodule.Logger;
import ru.guar7387.servermodule.api.ConnectionManager;
import ru.guar7387.servermodule.api.ConnectionRequest;
import ru.guar7387.servermodule.data.Article;

public class LoadAllArticlesTask extends AbstractTask {

    private static final String TAG = LoadAllArticlesTask.class.getSimpleName();

    private final Map<Integer, Article> mArticles;

    public LoadAllArticlesTask(int requestCode, ConnectionRequest request,
                               ConnectionManager connectionManager, Callback callback,
                               Map<Integer, Article> articles) {
        super(requestCode, request, connectionManager, callback);
        this.mArticles = articles;
    }

    @Override
    public void parseJson(JSONObject answer) {
        Logger.log(TAG, "answer - " + answer);
        try {
            JSONArray articles = answer.getJSONObject("response").getJSONArray("answer");
            for (int i = 0; i < articles.length(); i++) {
                try {
                    JSONArray array = articles.getJSONArray(i);
                    int id = array.getInt(0);
                    String title = array.getString(1);
                    String description = array.getString(2);
                    String url = array.getString(3);
                    String date = array.getString(4);
                    //noinspection ObjectAllocationInLoop
                    Article article = new Article(id, title, description, url, date);
                    mArticles.put(id, article);
                } catch (JSONException ignored) {
                }
            }
        } catch (JSONException ignored) {
        }
    }
}

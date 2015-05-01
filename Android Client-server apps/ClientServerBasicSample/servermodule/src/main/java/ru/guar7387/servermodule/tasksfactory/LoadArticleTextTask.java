package ru.guar7387.servermodule.tasksfactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.guar7387.servermodule.Logger;
import ru.guar7387.servermodule.api.ConnectionManager;
import ru.guar7387.servermodule.api.ConnectionRequest;
import ru.guar7387.servermodule.callbacks.FullArticleTextCallback;

public class LoadArticleTextTask extends AbstractTask {

    private static final String TAG = LoadArticleTextTask.class.getSimpleName();

    private final FullArticleTextCallback fullArticleCallback;

    private String htmlText;

    public LoadArticleTextTask(int requestCode, ConnectionRequest request, ConnectionManager connectionManager,
                               FullArticleTextCallback fullArticleCallback) {
        super(requestCode, request, connectionManager, null);
        this.fullArticleCallback = fullArticleCallback;
    }

    @Override
    public void parseJson(JSONObject answer) {
        Logger.log(TAG, "answer - " + answer);
        try {
            JSONArray article = answer.getJSONObject("response").getJSONArray("result");
            htmlText = article.getString(5);
        } catch (JSONException ignored) {
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        fullArticleCallback.onArticleLoaded(htmlText);
    }
}

package ru.guar7387.servermodule.tasksfactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.guar7387.servermodule.Logger;
import ru.guar7387.servermodule.api.ConnectionManager;
import ru.guar7387.servermodule.api.ConnectionRequest;
import ru.guar7387.servermodule.callbacks.ArticleCommentsCallback;
import ru.guar7387.servermodule.callbacks.Callback;

public class LoadArticleCommentsTask extends AbstractTask {

    private static final String TAG = LoadArticleCommentsTask.class.getSimpleName();

    private final ArticleCommentsCallback commentsCallback;

    private final List<Integer> commentsIds;

    public LoadArticleCommentsTask(int requestCode, ConnectionRequest request, ConnectionManager connectionManager,
                                   ArticleCommentsCallback commentsCallback) {
        super(requestCode, request, connectionManager, null);
        this.commentsCallback = commentsCallback;
        commentsIds = new ArrayList<>();
    }

    @Override
    public void parseJson(JSONObject answer) {
        Logger.log(TAG, "Answer - " + answer);
        try {
            JSONArray ids = answer.getJSONObject("response").getJSONArray("result");
            for (int i = 0; i < ids.length(); i++) {
                commentsIds.add(ids.getInt(i));
            }
        } catch (JSONException ignored) {
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        commentsCallback.onArticleCommentLoaded(commentsIds);
    }
}

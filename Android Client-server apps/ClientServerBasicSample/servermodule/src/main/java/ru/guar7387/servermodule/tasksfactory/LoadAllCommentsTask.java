package ru.guar7387.servermodule.tasksfactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import ru.guar7387.servermodule.callbacks.Callback;
import ru.guar7387.servermodule.Logger;
import ru.guar7387.servermodule.api.ConnectionManager;
import ru.guar7387.servermodule.api.ConnectionRequest;
import ru.guar7387.servermodule.data.Comment;

public class LoadAllCommentsTask extends AbstractTask {

    private static final String TAG = LoadAllCommentsTask.class.getSimpleName();

    private final Map<Integer, Comment> mComments;

    public LoadAllCommentsTask(int requestCode, ConnectionRequest request,
                               ConnectionManager connectionManager, Callback callback,
                               Map<Integer, Comment> comments) {
        super(requestCode, request, connectionManager, callback);
        this.mComments = comments;
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
                    int articleId = array.getInt(1);
                    int userId = array.getInt(2);
                    String text = array.getString(3);
                    //noinspection ObjectAllocationInLoop
                    Comment comment = new Comment(id, articleId, userId, text);
                    mComments.put(id, comment);
                } catch (JSONException ignored) {
                }
            }
        } catch (JSONException ignored) {
        }
    }
}
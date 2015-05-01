package ru.guar7387.servermodule.tasksfactory;

import org.json.JSONException;
import org.json.JSONObject;

import ru.guar7387.servermodule.api.ConnectionManager;
import ru.guar7387.servermodule.api.ConnectionRequest;
import ru.guar7387.servermodule.callbacks.Answer;
import ru.guar7387.servermodule.callbacks.AnswerCallback;

public class AddCommentTask extends AbstractTask {

    private Answer result;

    private final AnswerCallback callback;

    public AddCommentTask(int requestCode, ConnectionRequest request, ConnectionManager connectionManager,
                          AnswerCallback callback) {
        super(requestCode, request, connectionManager, null);
        this.callback = callback;
    }

    @Override
    public void parseJson(JSONObject answer) {
        result = Answer.FAIL;
        try {
            if (answer.getJSONObject("response").toString().equals("{}")) {
                result = Answer.OK;
            }
        } catch (JSONException ignored) {
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        callback.call(result);
    }
}



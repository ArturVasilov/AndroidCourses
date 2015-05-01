package ru.guar7387.clientserverwithvolleysample.server.requests.implementations;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ru.guar7387.clientserverwithvolleysample.data.Comment;
import ru.guar7387.clientserverwithvolleysample.data.User;
import ru.guar7387.clientserverwithvolleysample.server.callbacks.AddCommentCallback;
import ru.guar7387.clientserverwithvolleysample.server.callbacks.Answer;
import ru.guar7387.clientserverwithvolleysample.server.requests.AndroidTutorialsJsonRequest;
import ru.guar7387.clientserverwithvolleysample.utils.Logger;

public class AddCommentRequest extends AndroidTutorialsJsonRequest {

    private static final String TAG = AddCommentRequest.class.getSimpleName();

    private final Comment comment;

    private final User user;

    private final AddCommentCallback callback;

    public AddCommentRequest(Comment comment, User user, AddCommentCallback callback) {
        this.comment = comment;
        this.user = user;
        this.callback = callback;
    }

    @Override
    public Map<String, String> getParams() {
        return new HashMap<String, String>() {{
            put(ADD_COMMENT, "{\"token\":\"" + API_KEY + "\"," +
                    "\"user_id\":\"" + user.getId() + "\"," +
                    "\"password\":\"" + user.getPassword() + "\"," +
                    "\"article_id\":\"" + comment.getArticleId() + "\"," +
                    "\"text\":\"" + comment.getText() + "\"}");
        }};
    }

    @Override
    public Response.Listener<JSONObject> getSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Logger.log(TAG, "Response - " + response);
                    JSONObject answer = response.getJSONObject("response");
                    if (!answer.getString("answer").equalsIgnoreCase("ok")) {
                        callback.call(Answer.FAIL, 0);
                        return;
                    }
                    int id = answer.getInt("id");
                    callback.call(Answer.OK, id);
                } catch (JSONException ignored) {
                }
            }
        };
    }
}
